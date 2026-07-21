package hn.uth.monitordesuscripciones.database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLiteConnectionManager {
    private static final SQLiteConnectionManager INSTANCE = new SQLiteConnectionManager();
    private final String jdbcUrl;

    private SQLiteConnectionManager() {
        Path dbPath = resolveDatabasePath();
        createParentDirectory(dbPath);
        this.jdbcUrl = "jdbc:sqlite:" + dbPath.toAbsolutePath().toString().replace('\\', '/');

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("No se encontro el driver JDBC de SQLite en el classpath", ex);
        }
    }

    private static Path resolveDatabasePath() {
        String customPath = System.getProperty("monitor.db.path");
        if (customPath == null || customPath.trim().isEmpty()) {
            customPath = System.getenv("MONITOR_DB_PATH");
        }
        if (customPath != null && !customPath.trim().isEmpty()) {
            return Paths.get(customPath.trim()).toAbsolutePath();
        }

        String programData = System.getenv("PROGRAMDATA");
        if (programData != null && !programData.trim().isEmpty()) {
            return Paths.get(programData, "Monitor-de-Suscripciones", "monitor_suscripciones.db").toAbsolutePath();
        }

        String catalinaBase = System.getProperty("catalina.base");
        if (catalinaBase != null && !catalinaBase.trim().isEmpty()) {
            return Paths.get(catalinaBase, "data", "Monitor-de-Suscripciones", "monitor_suscripciones.db").toAbsolutePath();
        }

        return Paths.get(System.getProperty("user.home"), ".monitor-suscripciones", "monitor_suscripciones.db").toAbsolutePath();
    }

    private static void createParentDirectory(Path dbPath) {
        Path parent = dbPath.getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo crear el directorio para la base de datos: " + parent, ex);
        }
    }

    public static SQLiteConnectionManager getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl);
    }
}

