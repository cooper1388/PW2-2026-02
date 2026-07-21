package hn.uth.monitordesuscripciones.database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

public final class DatabaseInitializer {
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private DatabaseInitializer() {
    }

    public static void initialize() {
        if (INITIALIZED.get()) {
            return;
        }
        synchronized (DatabaseInitializer.class) {
            if (INITIALIZED.get()) {
                return;
            }
            try (Connection connection = SQLiteConnectionManager.getInstance().getConnection();
                 Statement statement = connection.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON");

                statement.execute("CREATE TABLE IF NOT EXISTS roles (" +
                        "id INTEGER PRIMARY KEY, " +
                        "nombre TEXT NOT NULL UNIQUE)");

                statement.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "correo TEXT NOT NULL UNIQUE, " +
                        "password TEXT NOT NULL, " +
                        "rol_id INTEGER NOT NULL, " +
                        "aprobado INTEGER NOT NULL DEFAULT 0, " +
                        "FOREIGN KEY(rol_id) REFERENCES roles(id))");

                statement.execute("CREATE TABLE IF NOT EXISTS categorias (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL UNIQUE, " +
                        "descripcion TEXT)");

                statement.execute("CREATE TABLE IF NOT EXISTS suscripciones (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuario_id INTEGER NOT NULL, " +
                        "categoria_id INTEGER NOT NULL, " +
                        "servicio TEXT NOT NULL, " +
                        "costo REAL NOT NULL, " +
                        "fecha_renovacion TEXT NOT NULL, " +
                        "recurrencia TEXT NOT NULL, " +
                        "FOREIGN KEY(usuario_id) REFERENCES usuarios(id), " +
                        "FOREIGN KEY(categoria_id) REFERENCES categorias(id))");

                statement.execute("CREATE TABLE IF NOT EXISTS historial_uso_suscripciones (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "suscripcion_id INTEGER NOT NULL, " +
                        "fecha_uso TEXT NOT NULL, " +
                        "usada INTEGER NOT NULL, " +
                        "FOREIGN KEY(suscripcion_id) REFERENCES suscripciones(id), " +
                        "UNIQUE(suscripcion_id, fecha_uso))");

                statement.execute("CREATE INDEX IF NOT EXISTS idx_usuarios_correo ON usuarios(correo)");
                statement.execute("CREATE INDEX IF NOT EXISTS idx_suscripciones_usuario ON suscripciones(usuario_id)");
                statement.execute("CREATE INDEX IF NOT EXISTS idx_historial_suscripcion_fecha ON historial_uso_suscripciones(suscripcion_id, fecha_uso)");

                statement.execute("INSERT OR IGNORE INTO roles(id, nombre) VALUES (1, 'ADMIN')");
                statement.execute("INSERT OR IGNORE INTO roles(id, nombre) VALUES (2, 'USUARIO')");

                statement.execute("INSERT OR IGNORE INTO categorias(nombre, descripcion) VALUES ('Entretenimiento', 'Servicios de video y streaming')");
                statement.execute("INSERT OR IGNORE INTO categorias(nombre, descripcion) VALUES ('Musica', 'Servicios de audio y musica')");
                statement.execute("INSERT OR IGNORE INTO categorias(nombre, descripcion) VALUES ('Educacion', 'Plataformas educativas')");

                statement.execute("INSERT OR IGNORE INTO usuarios(id, nombre, correo, password, rol_id, aprobado) " +
                        "VALUES (1, 'Administrador', 'admin@local', 'admin123', 1, 1)");

                INITIALIZED.set(true);
            } catch (Exception ex) {
                throw new IllegalStateException("No se pudo inicializar SQLite", ex);
            }
        }
    }
}

