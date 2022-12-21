package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import org.json.JSONArray;

// Bond/fixed income (price, coupon, yield, expDate)
// yield is the same as coupon rate

public class Bonds implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private final String bondSymbol;
    private final double faceValue;
    private double quantity;
    private final double couponRate;
    private final double bondYield;
    private final int expMonth;
    private final int expYear;
    static BondMarket bondMarket = new BondMarket();

    public Bonds(
            String bondSymbol, double faceValue, double quantity,
            double couponRate, double bondYield, int expMonth, int expYear) {
        this.bondSymbol = bondSymbol;
        this.faceValue = faceValue;
        this.quantity = quantity;
        this.couponRate = couponRate;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.bondYield = bondYield;
    }

    //method to make the http call
    private static JSONArray getJsonArrayBond(int name) throws IOException, InterruptedException {

        return bondMarket.getJsonArrayBond(name);
    }

    //method to sell a bond
    public double sellBonds(double bondQuantity) {
        double sellPrice = setValue(this.faceValue);
        if (bondQuantity <= this.quantity) {
            this.quantity -= bondQuantity;
            sellPrice = setValue(sellPrice);
            return sellPrice * bondQuantity;
        }
        return 0;
    }

    //method to set/buy a bond's information
    public static double getBondInfoBondYield(int name) throws IOException, InterruptedException {
        JSONArray obj = getJsonArrayBond(name);
        double getBondYield;
        String avgMedYield = "averageMedianYield";
        if ("".equals(obj.getJSONObject(0).getString(avgMedYield))){
            getBondYield = Double.parseDouble(obj.getJSONObject(1).getString(avgMedYield));

        } else {
            getBondYield = Double.parseDouble(obj.getJSONObject(0).getString(avgMedYield));
        }

        return getBondYield;
    }
    public static double getBondInfoCouponRate(int name) throws IOException, InterruptedException {
        JSONArray obj = getJsonArrayBond(name);
        return Double.parseDouble(obj.getJSONObject(0).getString("interestRate"));
    }

    public double getPresentValue() {
        // Formula taken from https://www.wallstreetmojo.com/bond-pricing-formula/
        double n = 2; //treasury bonds are semi-annual
        int t = expYear - 2022;
        double ytm = 0.0377; // This value (3.77%) was taken from averaging multiple YTMs for different bonds using data from https://ycharts.com/indicators
        // (ex. YTM for a 7-year bond is 3.69 from https://ycharts.com/indicators/7_year_treasury_rate )
        double newCouponRate = couponRate/100;

        double valueFromInterest = ( 1 - Math.pow( ( 1 + (ytm / n) ),( -n * t ))) / (ytm / n);
        double valueFromRedemption = faceValue / Math.pow( ( 1 + (ytm / 2)),( n * t));
        double coupon = ( faceValue * ( newCouponRate ) ) / n;
        double pv = coupon * valueFromInterest + valueFromRedemption;

        return setValue(pv * quantity);
    }

    public static int setYear(int name){
        if ((name == 30 || name == 20 ||
                name == 10|| name == 7||
                name == 5|| name == 3||
                name == 2)){
            return name;
        }
        else{
            return 0;
        }
    }
    public void addQuantity(double newQuantity) {
        this.quantity += newQuantity;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public String getBondSymbol() {
        return this.bondSymbol;
    }

    public double setValue(double bondFaceValue){
        return Math.round(bondFaceValue * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Bond Name: " + this.bondSymbol  + ", Face Value: " + this.faceValue
                + ", Coupon Rate: " + this.couponRate
                + ", Quantity: " + this.quantity + ", Yield: " + this.bondYield
                + ", Expiration Date: " + this.expMonth + "/" + this.expYear + "\n";
    }
}