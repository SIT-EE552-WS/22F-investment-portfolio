package edu.investmentportfolio;
//Bond/fixed income (price, coupon, yield, expdate)

public class Bonds {
    private double price;
    private double yield;
    private double expdate;

    public Bonds(double price, double coupon, double yield, double expdate) {
        this.price = price;
        this.yield = yield;
        this.expdate = expdate;
    }

    public static void buy(double bond) {
        // buying the bond
    }

    public static void cashIn(double bond) {
        // cashing in the bond, should increase the over cash of the user
    }

    public double getPrice() {
        return this.price;
    }

    public double getYield() {
        return this.yield;
    }

    public double getExpdate() {
        return this.expdate;
    }

}
