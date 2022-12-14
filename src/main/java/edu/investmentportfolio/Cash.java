package edu.investmentportfolio;

import java.io.Serial;
import java.io.Serializable;

public class Cash implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private double balance;
    private final double interest;

    public Cash() {
        this.balance = 0.00;
        this.interest = 0.25;
    }

    public void withdraw(double cashAmount) {
        if (cashAmount < this.balance) {
            this.balance = setMoney(this.balance);
            cashAmount = setMoney(cashAmount);
            this.balance -= cashAmount;
        }
    }

    public void deposit(double cashAmount) {
        this.balance = this.balance + cashAmount;
        setMoney(this.balance);
    }

    public double getBalance() {
        return setMoney(this.balance);
    }

    public void addInterest() {
        this.balance = this.balance * this.interest + this.balance;
        setMoney(this.balance);
    }

    @Override
    public String toString() {
        addInterest();
        return "Cash Balance: " + setMoney(this.balance) + ", Interest Rate: " + this.interest + "\n";
    }

    public double setMoney(double cashBalance) {
        return Math.round(cashBalance * 100.0) / 100.0;
    }
}
