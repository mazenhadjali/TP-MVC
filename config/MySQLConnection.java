package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    private static MySQLConnection instance;
    private static Connection connection;

    String unicode = "?useUnicode=yes&characterEncoding=UTF-8";

    private String url = "jdbc:mysql://localhost:3306/tpmvc";
    private String username = "root";
    private String password = "root";

    private MySQLConnection() {
        try {
            connection = DriverManager.getConnection(url + unicode, username, password);
            createPersonTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPersonTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLQueries.CREATE_PERSON_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Public method to get the Singleton instance
    public static synchronized MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    // Public method to get the database connection
    public Connection getConnection() {
        try {
            // If the connection is null or closed, create a new one
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url + unicode, username, password);
                createPersonTable(); // Ensure the necessary tables are created
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
