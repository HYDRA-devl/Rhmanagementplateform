package fr.gestionrh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/rh?serverTimezone=UTC";
            String username = "root";
            String password = "ahmed";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("MySQL JDBC Driver not found." + e);
        } catch (SQLException e) {
            throw new SQLException("Connection failed! Check output console" + e);
        }
    }
}