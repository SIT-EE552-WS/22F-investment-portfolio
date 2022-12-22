package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


import org.json.JSONArray;

// Bond/fixed income (price, coupon, yield, expDate)
// yield is the same as coupon rate
// The source below states Treasury Bonds can be purchased as fractions, so we set quantity as BigDecimal
// https://www.cmegroup.com/education/courses/introduction-to-treasuries/calculating-us-treasury-pricing.html

public class Bonds implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private final String bondSymbol;
    private final BigDecimal faceValue;
    private BigDecimal quantity;
    private final BigDecimal couponRate;
    private final BigDecimal bondYield;
    private final int expMonth;
    private final int expYear;
    static BondMarket bondMarket = new BondMarket();

    public Bonds(
            String bondSymbol, BigDecimal faceValue, BigDecimal quantity,
            BigDecimal couponRate, BigDecimal bondYield, int expMonth, int expYear) {
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
    public BigDecimal sellBonds(BigDecimal bondQuantity) {
        BigDecimal sellPrice = setValue(this.faceValue);

        if (bondQuantity.compareTo(this.quantity) <= 0) {
            this.quantity = this.quantity.subtract(bondQuantity);
            sellPrice = setValue(sellPrice);
            return sellPrice.multiply(bondQuantity);
        }
        return BigDecimal.ZERO;
    }

    //method to set/buy a bond's information
    public static BigDecimal getBondInfoBondYield(int name) throws IOException, InterruptedException {
        JSONArray obj = getJsonArrayBond(name);
        BigDecimal getBondYield;
        String avgMedYield = "averageMedianYield";
        if ("".equals(obj.getJSONObject(0).getString(avgMedYield))){
            getBondYield = BigDecimal.valueOf(Double.parseDouble((obj.getJSONObject(1).getString(avgMedYield))));

        } else {
            getBondYield = BigDecimal.valueOf(Double.parseDouble((obj.getJSONObject(0).getString(avgMedYield))));
        }

        return getBondYield;
    }
    public static BigDecimal getBondInfoCouponRate(int name) throws IOException, InterruptedException {
        JSONArray obj = getJsonArrayBond(name);
        return BigDecimal.valueOf( Double.parseDouble( obj.getJSONObject(0).getString("interestRate")));
    }

    public BigDecimal getPresentValue() {
        // Formula taken from https://www.wallstreetmojo.com/bond-pricing-formula/
        BigDecimal n = BigDecimal.valueOf(2); //treasury bonds are semi-annual
        int t = expYear - 2022;
        BigDecimal ytm = BigDecimal.valueOf(0.0377); // This value (3.77%) was taken from averaging multiple YTMs for different bonds using data from https://ycharts.com/indicators
        // (ex. YTM for a 7-year bond is 3.69 from https://ycharts.com/indicators/7_year_treasury_rate )
        BigDecimal newCouponRate = couponRate.divide(BigDecimal.valueOf(100));


        BigDecimal base =(BigDecimal.ONE.add(ytm.divide(n)) );
        BigDecimal exp = ((n.negate()).multiply(BigDecimal.valueOf(t)));
        BigDecimal power = BigDecimal.valueOf(Math.pow( Double.valueOf(String.valueOf(base)), Double.valueOf(String.valueOf(exp))));

        BigDecimal valueFromInterest = (BigDecimal.ONE.subtract(power)).divide(ytm.divide(n), 6, RoundingMode.HALF_EVEN);

        BigDecimal base2 = BigDecimal.ONE.add(ytm.divide(BigDecimal.valueOf(2)));
        BigDecimal exp2 = n.multiply(BigDecimal.valueOf(t));
        BigDecimal power2 = BigDecimal.valueOf(Math.pow( Double.valueOf(String.valueOf(base2)), Double.valueOf(String.valueOf(exp2))));

        BigDecimal valueFromRedemption = faceValue.divide(power2, 6, RoundingMode.HALF_EVEN);

        BigDecimal coupon = ( faceValue.multiply(newCouponRate)).divide(n);
        BigDecimal pv = (coupon.multiply(valueFromInterest)).add(valueFromRedemption);

        return setValue(pv.multiply(quantity));
    }

    public static int setYear(int name){
        if ((name == 30 || name == 20 || name == 10||
                name == 7|| name == 5|| name == 3|| name == 2)){
            return name;
        }
        else{
            return 0;
        }
    }
    public void addQuantity(BigDecimal newQuantity) {
        this.quantity =quantity.add(newQuantity);
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public String getBondSymbol() {
        return this.bondSymbol;
    }

    public BigDecimal setValue(BigDecimal bondFaceValue){

        return bondFaceValue.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return "Bond Name: " + this.bondSymbol  + ", Face Value: " + this.faceValue
                + ", Coupon Rate: " + this.couponRate
                + ", Quantity: " + this.quantity + ", Yield: " + this.bondYield
                + ", Expiration Date: " + this.expMonth + "/" + this.expYear + "\n";
    }
}