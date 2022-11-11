package com.revature.dao;

import com.revature.model.Account;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public Account createAccount(double initialBalance, int bankUserId) throws SQLException, IOException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "insert into bank_accounts (balance, bank_users_id) \n" +
                    "values \n" +
                    "(?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, initialBalance);
            ps.setInt(2, bankUserId);

            ps.execute(); // execute query

            ResultSet rs = ps.getGeneratedKeys(); // get the automatically generated id
            rs.next();
            int id = rs.getInt(1);

            return new Account(id, initialBalance, bankUserId);
        }
    }

    public List<Account> findAccountByUserId(int userId) throws SQLException, IOException {
        try (Connection connection = ConnectionUtility.getConnection()) {
            String sql = "select *\n" +
                    "from bank_accounts\n" +
                    "where bank_users_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            List<Account> accounts = new ArrayList<>();

            while (rs.next()) {
                accounts.add(
                        new Account(
                                rs.getInt("id"),
                                rs.getDouble("balance"),
                                rs.getInt("bank_users_id")
                        )
                );
            }

            return accounts;
        }
    }

    public List<Account> findAllAccounts() throws SQLException, IOException {
        try (Connection connection = ConnectionUtility.getConnection()) {
            String sql = "select *\n" +
                    "from bank_accounts";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            List<Account> accounts = new ArrayList<>();

            while (rs.next()) {
                accounts.add(
                        new Account(
                                rs.getInt("id"),
                                rs.getDouble("balance"),
                                rs.getInt("bank_users_id")
                        )
                );
            }

            return accounts;
        }
    }
}
