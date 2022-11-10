package com.revature.dao;

import com.revature.Main;
import com.revature.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectionUtility {

    public static Connection getConnection() throws SQLException, IOException {
        InputStream props = Main.class.getClassLoader().getResourceAsStream("database_config.properties");
        Properties properties = new Properties();
        properties.load(props);
        boolean useH2 = Boolean.parseBoolean(properties.getProperty("use-h2"));

        if (useH2) { // Use H2 in-memory database
            return DriverManager.getConnection("jdbc:h2:mem:test");
        } else { // Use Postgres database
            String url = "jdbc:postgresql://database-1.cifwcr7ybkhx.us-east-1.rds.amazonaws.com:5432/postgres";
            String username = "postgres";
            String password = "password"; // Highly recommend to utilize environment variables to store
            // database credentials instead of hardcoding

            return DriverManager.getConnection(url, username, password);
        }
    }

    public static void populateH2Database(Connection con) throws SQLException, IOException {
        String createTableSql = "create table bank_users (\n" +
                "\tid SERIAL primary key,\n" +
                "\tusername VARCHAR(200) not null unique,\n" +
                "\tpassword varchar(200) not null,\n" +
                "\trole varchar(20) not null\n" +
                ")";

        String insertUsers = "insert into bank_users (username, password, role)\n" +
                "values \n" +
                "('admin123', 'password123', 'admin'),\n" +
                "('customer123', 'test12345', 'customer')";

        PreparedStatement ps1 = con.prepareStatement(createTableSql);
        ps1.executeUpdate();

        PreparedStatement ps2 = con.prepareStatement(insertUsers);
        ps2.executeUpdate();

//        String selectUsers = "select * from bank_users where username = ? and password = ?";
//        PreparedStatement ps3 = con.prepareStatement(selectUsers);
//        ps3.setString(1, "john_doe");
//        ps3.setString(2, "password123");
//        ResultSet rs = ps3.executeQuery();
//
//        if (rs.next()) {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setUsername(rs.getString("username"));
//            user.setPassword(rs.getString("password"));
//            user.setRole(rs.getString("role"));
//
//            System.out.println(user);
//        }
//
//        UserDAO ud = new UserDAO();
//        User u1 = ud.findUserByUsernameAndPassword("john_doe", "password123");
//        System.out.println(u1);
    }

    // Ideally, a test should NOT rely on another test case. Therefore after each test case, we
    // want to start from scratch in the H2 database
    public static void clearH2Database(Connection con) throws SQLException {
        String sql = "DROP ALL OBJECTS"; // H2 specific

        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

}
