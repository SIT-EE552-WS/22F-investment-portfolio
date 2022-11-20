package edu.investmentportfolio;
//Bond/fixed income (price, coupon, yield, expdate)

public class Bonds {
    private double price;
    private double coupon;
    private double yield;
    private double expdate;

    public Bonds(double price, double coupon, double yield, double expdate) {
        this.price = price;
        this.coupon = coupon;
        this.yield = yield;
        this.expdate = expdate;
    }

    public double getPrice() {
        return this.price;
    }

    public double getCoupon() {
        return this.coupon;
    }

    public double getYield() {
        return this.yield;
    }

    public double getExpdate() {
        return this.expdate;
    }

}
