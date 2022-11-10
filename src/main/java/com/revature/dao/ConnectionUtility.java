package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://database-1.cifwcr7ybkhx.us-east-1.rds.amazonaws.com:5432/postgres";
        String username = "postgres";
        String password = "password"; // Highly recommend to utilize environment variables to store
        // database credentials instead of hardcoding

        return DriverManager.getConnection(url, username, password);
    }

}
