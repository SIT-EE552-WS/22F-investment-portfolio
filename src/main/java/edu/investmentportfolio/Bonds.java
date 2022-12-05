package edu.investmentportfolio;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

//Bond/fixed income (price, coupon, yield, expdate)
// yield is the same as coupon rate

public class Bonds implements Serializable {
    private static final long serialVersionUID = 4L;
    private String bondSymbol;
    private double faceValue;
    private double quantity;
    private double coupon;
    private double yield;
    private int expMonth;
    private int expYear;
    // 1 3 4 6
    //131514208
    final double month1US = 3.829;
    final double month3US = 4.327;
    final double month4US = 4.522;
    final double month6US = 4.7;
    // 1 2 3 5 7 10 20 30
    //
    final double year1US = 4.711;
    final double year2US = 4.309;
    final double year3US = 4.022;
    final double year5US = 3.697;
    final double year7US = 3.635;
    final double year10US = 3.532;
    final double year20US = 3.804;
    final double year30US = 3.582;

    public Bonds() {
    }
    public Bonds(String bondSymbol,  double faceValue, double quantity, double coupon, double yield, int expMonth, int expYear) {
        this.bondSymbol = bondSymbol;
        this.faceValue = faceValue;
        this.quantity = quantity;
        this.coupon = coupon;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.yield = yield;
    }

    public static void buy(double bond) {
        // buying the bond
    }

    public static void cashIn(double bond) {
        // cashing in the bond, should increase the over cash of the user
    }

    public double getFaceValue() {return this.faceValue;}

    public double getYield() {
        return this.yield;
    }

    public double getExpMonth() {
        return this.expMonth;
    }
    public double getExpYear() {
        return this.expYear;
    }

    //Like Stocks this is just getting the sell price


    public double buyBonds(String name, double faceValue, double quantity) throws IOException, InterruptedException {
        /*
            month3US =3.829; 0
            month4US = 4.488; 0
            month6US = 4.666; 0
            year1US = 4.69; 0
            year2US = 4.279; 4.50
            year3US = 3.986; 4.50
            year5US = 3.652; 3.875
            year7US = 3.588; 3.875
            year10US = 3.486; 4.125
            year20US = 3.764; 4
            year30US = 3.541; 4.00
         */
        Properties props = new Properties();
        InputStream inputStream = Stock.class.getClassLoader().getResourceAsStream("BondsClassifier.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }
        int num = setYear(name);
        String combine = "cusip"+num;
        String cusip = props.getProperty(combine);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.treasurydirect.gov/TA_WS/securities/search?cusip="+ cusip+"&format=json"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        JSONArray obj = new JSONArray(response.body());
        //System.out.println(obj.getJSONObject(0).getString("pricePer100"));
        double price = Double.parseDouble(obj.getJSONObject(0).getString("pricePer100"));
        System.out.println(combine + " / " + price);
        if (price != 0) {
            return price;
        } else {
            System.out.println("Invalid bond name.");
            return 0;
        }
    }



    public static int setYear(String name) throws IOException, InterruptedException {
        if (name == "30year") {
            return 30;
        } else if (name == "20year") {
            return 20;
        } else if (name == "10year") {
            return 10;
        } else if (name == "7year") {
            return 7;
        } else if (name == "5year") {
            return 5;
        } else if (name == "3year") {
            return 3;
        } else if (name == "2year") {
            return 2;
        } else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Bond Name: " + bondSymbol +", Face Value: " + faceValue + ", Quantity: " + quantity +  ", Expiration Date: " + expMonth + "/" + expYear + "\n";
    }
    public String viewBonds(String name, double quantity) throws IOException, InterruptedException {
        if (faceValue == 0) {
            return "Invalid stock name.";
        }
        //setfaceValue();
        return "Bond Name: " + bondSymbol +", Face Value: " + faceValue + ", Quantity: " + quantity +  ", Expiration Date: " + expMonth + "/" + expYear + "\n";
    }

    public void addQuantity(double quantity2) {
        this.quantity += quantity2;
    }

    public double getQuantity() {
        return quantity;
    }

    public double sellBonds(String name, double faceValue, double quantity2) throws IOException, InterruptedException {
        double sellPrice = buyBonds(name, faceValue, quantity2);
        if (quantity2 > this.quantity) {
            System.out.println("You do not have enough bonds to sell that amount.");
            return 0;
        } else {
            this.quantity -= quantity2;
            sellPrice = Math.round(sellPrice * 100.0) / 100.0;
            return sellPrice * quantity2;
        }
    }
    public void setfaceValue() {
        this.faceValue = Math.round(this.faceValue * 100.0) / 100.0;
    }
}