package edu.investmentportfolio;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.json.JSONArray;

// Bond/fixed income (price, coupon, yield, expDate)
// yield is the same as coupon rate

@SuppressWarnings("SpellCheckingInspection")
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
    private static JSONArray getJsonArrayBond(String name) throws IOException, InterruptedException {
        Properties props = new Properties();
        InputStream inputStream = Stock.class.getClassLoader().getResourceAsStream("BondsClassifier.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }
        int num = setYear(name);
        String combine = "cusip" + num;
        String cusip = props.getProperty(combine);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://www.treasurydirect.gov/TA_WS/securities/search?cusip=" + cusip + "&format=json"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONArray(response.body());
    }

    // method to buy a bond
    public double buyBond(String name) throws IOException, InterruptedException {
        JSONArray obj = getJsonArrayBond(name);
        return Double.parseDouble(obj.getJSONObject(0).getString("pricePer100"));
    }

    //method to sell a bond
    public double sellBonds(double bondQuantity) {
        double sellPrice = setValue(this.faceValue);
        if (bondQuantity < this.quantity) {
            this.quantity -= bondQuantity;
            sellPrice = setValue(sellPrice);
            return sellPrice * bondQuantity;
        }
        return 0;
    }

    //method to get a bond's information
    public static double getBondInfo(String name, int numChoice) throws IOException, InterruptedException {
        JSONArray obj = getJsonArrayBond(name);
        double getBondYield;
        String avgMedYield = "averageMedianYield";
        if ("".equals(obj.getJSONObject(0).getString(avgMedYield))){
            getBondYield = Double.parseDouble(obj.getJSONObject(1).getString(avgMedYield));

        } else {
            getBondYield = Double.parseDouble(obj.getJSONObject(0).getString(avgMedYield));
        }

        double couRate = Double.parseDouble(obj.getJSONObject(0).getString("interestRate"));

        return switch (numChoice) {
            case 1 -> getBondYield;
            case 2 -> couRate;
            default -> 0;
        };
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

    public static int setYear(String name){

        if ("30".equals(name)) {
            return 30;
        } else if ("20".equals(name)) {
            return 20;
        } else if ("10".equals(name)) {
            return 10;
        } else if ("7".equals(name)) {
            return 7;
        } else if ("5".equals(name)) {
            return 5;
        } else if ("3".equals(name)) {
            return 3;
        } else if ("2".equals(name)) {
            return 2;
        } else {
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