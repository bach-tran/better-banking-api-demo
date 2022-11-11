package com.revature.dto;

import java.util.Objects;

public class InitialBankAccountInformation {

    private double initialBalance;

    public InitialBankAccountInformation() {
    }

    public InitialBankAccountInformation(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitialBankAccountInformation that = (InitialBankAccountInformation) o;
        return Double.compare(that.initialBalance, initialBalance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialBalance);
    }

    @Override
    public String toString() {
        return "InitialBankAccountInformation{" +
                "initialBalance=" + initialBalance +
                '}';
    }
}
