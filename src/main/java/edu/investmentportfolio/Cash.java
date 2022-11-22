package edu.investmentportfolio;

public class Cash {
    private double balance;
    private double interest;

    public Cash(double balance, double interest) {
        this.balance = balance;
        this.interest = interest;
    }

    // should be able to read from the command line to check the balance in order to
    // withdraw
    public double withdraw(double cashAmount) {
        this.balance = this.balance - cashAmount;
        return this.balance;
    }

    public double deposit(double cashAmount) {
        this.balance = this.balance + cashAmount;
        return this.balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getInterest() {
        return this.interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    // method called calculate interest added to the balance monthly
    public void addInterest() {
        this.balance = this.balance * this.interest + this.balance;
    }

}
