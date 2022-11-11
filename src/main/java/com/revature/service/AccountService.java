package com.revature.service;

import com.revature.dao.AccountDAO;
import com.revature.model.Account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AccountService {

    private AccountDAO accountDao = new AccountDAO();

    public Account addAccount(double initialBalance, int bankUserId) throws SQLException, IOException {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be less than 0");
        }

        return accountDao.createAccount(initialBalance, bankUserId);
    }

    public List<Account> getAccountsByUserId(int userId) throws SQLException, IOException {
        return accountDao.findAccountByUserId(userId);
    }

    public List<Account> getAllAccounts() throws SQLException, IOException {
        return accountDao.findAllAccounts();
    }
}
