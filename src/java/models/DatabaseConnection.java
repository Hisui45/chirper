package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3307/twitterclone";

        String username = "cloneuser";
        String password = "test";
        
        Connection connection = DriverManager.getConnection(
        dbURL, username, password);
        
        return connection;
    }
    
}
