PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    correo TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    rol_id INTEGER NOT NULL,
    aprobado INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS categorias (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS suscripciones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id INTEGER NOT NULL,
    categoria_id INTEGER NOT NULL,
    servicio TEXT NOT NULL,
    costo REAL NOT NULL,
    fecha_renovacion TEXT NOT NULL,
    recurrencia TEXT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS historial_uso_suscripciones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    suscripcion_id INTEGER NOT NULL,
    fecha_uso TEXT NOT NULL,
    usada INTEGER NOT NULL,
    FOREIGN KEY (suscripcion_id) REFERENCES suscripciones(id),
    UNIQUE(suscripcion_id, fecha_uso)
);

CREATE INDEX IF NOT EXISTS idx_usuarios_correo ON usuarios(correo);
CREATE INDEX IF NOT EXISTS idx_suscripciones_usuario ON suscripciones(usuario_id);
CREATE INDEX IF NOT EXISTS idx_historial_suscripcion_fecha ON historial_uso_suscripciones(suscripcion_id, fecha_uso);

INSERT OR IGNORE INTO roles(id, nombre) VALUES (1, 'ADMIN');
INSERT OR IGNORE INTO roles(id, nombre) VALUES (2, 'USUARIO');

INSERT OR IGNORE INTO categorias(nombre, descripcion) VALUES ('Entretenimiento', 'Servicios de video y streaming');
INSERT OR IGNORE INTO categorias(nombre, descripcion) VALUES ('Musica', 'Servicios de audio y musica');
INSERT OR IGNORE INTO categorias(nombre, descripcion) VALUES ('Educacion', 'Plataformas educativas');

INSERT OR IGNORE INTO usuarios(id, nombre, correo, password, rol_id, aprobado)
VALUES (1, 'Administrador', 'admin@local', 'admin123', 1, 1);

