/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectionDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

/**
 *
 * @author marianacunha
 */
public class SQLiteConnection {

    private static final String DB_URL = "jdbc:sqlite:usermanager.db";
    private static final Logger LOGGER = Logger.getLogger(SQLiteConnection.class.getName());
    private static boolean initialized = false;

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(DB_URL);
            
            if (!initialized) {
                initializeDatabase(connection);
                initialized = true;
            }
            
            return connection;
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Driver SQLite não encontrado.", e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao conectar ao banco.", e);
            throw new RuntimeException(e);
        }
    }

    private static void initializeDatabase(Connection connection) {
        String sqlUser = """
                CREATE TABLE IF NOT EXISTS Users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fullName TEXT NOT NULL,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    profile TEXT NOT NULL CHECK (profile IN ('ADMINISTRATOR', 'USER')),
                    isFirstAdmin INTEGER DEFAULT 0,
                    registrationDate TEXT NOT NULL,
                    isAuthorized INTEGER DEFAULT 0
                );
            """;

        String sqlNotification = """
                CREATE TABLE IF NOT EXISTS Notifications (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    userId INTEGER NOT NULL,
                    content TEXT NOT NULL,
                    isRead INTEGER DEFAULT 0,
                    sentDate TEXT NOT NULL,
                    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
                );
            """;

        String sqlConfig = """
                CREATE TABLE IF NOT EXISTS Config (
                    key TEXT PRIMARY KEY,
                    value TEXT NOT NULL
                );
            """;

        String sqlInitialConfig = """
                INSERT OR IGNORE INTO Config (key, value)
                VALUES ('logFormat', 'CSV');
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlUser);
            stmt.execute(sqlNotification);
            stmt.execute(sqlConfig);
            stmt.execute(sqlInitialConfig);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar o banco.", e);
        }
    }

    public static void closeQuietly(AutoCloseable... resources) {
        for (AutoCloseable res : resources) {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Erro ao fechar recurso.", e);
                }
            }
        }
    }
}

