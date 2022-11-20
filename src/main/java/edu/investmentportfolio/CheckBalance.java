package edu.investmentportfolio;

import java.io.Serializable;

public class CheckBalance implements Serializable {
    private double balance;
    private double interest;
    private double interestRate;

    public CheckBalance(double balance, double interest, double interestRate) {
        this.balance = balance;
        this.interest = interest;
        this.interestRate = interestRate;
    }

    public double getBalance() {
        return this.balance;
    }

    public boolean withdraw(double cashAmount) {
        this.balance = this.balance - cashAmount;
        return true;
    }

    public boolean deposit(double cashAmount) {
        this.balance = this.balance + cashAmount;
        return true;
    }
}
