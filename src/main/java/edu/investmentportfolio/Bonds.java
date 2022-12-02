package edu.investmentportfolio;
//Bond/fixed income (price, coupon, yield, expdate)

public class Bonds {
    private static final long serialVersionUID = 4L;
    private double price;
    private double yield;
    private double expdate;

    // 1 3 4 6
    final double month1US = 3.811;
    final double month3US = 4.315;
    final double month4US = 4.488;
    final double month6US = 4.666;
    // 1 2 3 5 7 10 20 30
    final double year1US = 4.69;
    final double year2US = 4.279;
    final double year3US = 3.986;
    final double year5US = 3.652;
    final double year7US = 3.588;
    final double year10US = 3.486;
    final double year20US = 3.764;
    final double year30US = 3.541;
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
