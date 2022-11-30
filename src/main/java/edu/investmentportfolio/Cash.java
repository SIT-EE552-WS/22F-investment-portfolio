package edu.investmentportfolio;

import java.io.Serializable;

public class Cash implements Serializable {
    private static final long serialVersionUID = 4L;
    private double balance;
    private double interest;

    public Cash() {
        this.balance = 0.0;
        this.interest = 0.25;
    }

    public double withdraw(double cashAmount) {
        if (cashAmount > this.balance) {
            System.out.println("You do not have enough money to withdraw that amount.");
            return 0;
        } else {
            this.balance -= cashAmount;
            return cashAmount;
        }
    }

    public double deposit(double cashAmount) {
        this.balance = this.balance + cashAmount;
        return this.balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addInterest() {
        this.balance = this.balance * this.interest + this.balance;
    }

    @Override
    public String toString() {
        return "Cash Balance: " + balance + ", Interest Rate: " + interest + "\n";
    }

}
