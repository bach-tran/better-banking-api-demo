package com.revature.model;

import java.util.Objects;

public class Account {

    private int id;
    private double balance;
    private int bankUserId;

    public Account() {
    }

    public Account(int id, double balance, int bankUserId) {
        this.id = id;
        this.balance = balance;
        this.bankUserId = bankUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getBankUserId() {
        return bankUserId;
    }

    public void setBankUserId(int bankUserId) {
        this.bankUserId = bankUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Double.compare(account.balance, balance) == 0 && bankUserId == account.bankUserId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, bankUserId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", bankUserId=" + bankUserId +
                '}';
    }
}
