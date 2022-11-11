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
            return DriverManager.getConnection("jdbc:h2:mem:test"); // Connect to H2 database
        } else { // Use Postgres database
            String url = "jdbc:postgresql://database-1.cifwcr7ybkhx.us-east-1.rds.amazonaws.com:5432/postgres";
            String username = "postgres";
            String password = "password"; // Highly recommend to utilize environment variables to store
            // database credentials instead of hardcoding

            return DriverManager.getConnection(url, username, password);
        }
    }

    public static void populateH2Database(Connection con) throws SQLException, IOException {
        String createBankUsersTableSql = "create table bank_users (\n" +
                "\tid SERIAL primary key,\n" +
                "\tusername VARCHAR(200) not null unique,\n" +
                "\tpassword varchar(200) not null,\n" +
                "\trole varchar(20) not null\n" +
                ")";

        String insertBankUsersSql = "insert into bank_users (username, password, role)\n" +
                "values \n" +
                "('admin123', 'password123', 'admin'),\n" +
                "('customer123', 'test12345', 'customer')";

        String createBankAccountsTableSql = "create table bank_accounts (\n" +
                "\tid SERIAL primary key,\n" +
                "\tbalance numeric(11, 2) not null check(balance >= 0),\n" +
                "\tbank_users_id INTEGER references bank_users(id) -- foreign key\n" +
                ")";

        String insertBankAccountsSql = "insert into bank_accounts (balance, bank_users_id) \n" +
                "values \n" +
                "(500.43, 2),\n" +
                "(112.12, 2)";

        PreparedStatement ps1 = con.prepareStatement(createBankUsersTableSql);
        ps1.execute();

        PreparedStatement ps2 = con.prepareStatement(insertBankUsersSql);
        ps2.execute();

        PreparedStatement ps3 = con.prepareStatement(createBankAccountsTableSql);
        ps3.execute();

        PreparedStatement ps4 = con.prepareStatement(insertBankAccountsSql);
        ps4.execute();
    }

    // Ideally, a test should NOT rely on another test case. Therefore after each test case, we
    // want to start from scratch in the H2 database
    public static void clearH2Database(Connection con) throws SQLException {
        String sql = "DROP ALL OBJECTS"; // H2 specific

        Statement statement = con.createStatement();
        statement.execute(sql);
    }

}
