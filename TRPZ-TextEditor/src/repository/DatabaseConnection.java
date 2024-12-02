package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:data/TextEditor";

    // Метод для підключення до бази даних
    public static Connection connect() {
        try {
            System.out.println("Connected to database");
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Not connected to database");
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
