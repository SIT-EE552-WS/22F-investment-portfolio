package edu.investmentportfolio;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;

// Possible examples include bitcoin, ethereum, tether, binancecoin,theta-token, etc.
// Anything under "id" on this page should work:
// https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd
public class Crypto implements Serializable{
    private static final long serialVersionUID = 4L;
    private String cryptoname;
    private double price;
    private double quantity;
    // private double dividend;
    // private double yield;

    public Crypto() {
    }

    public Crypto(String cryptoname, double price, double quantity) {
        this.cryptoname = cryptoname;
        this.price = price;
        this.quantity = quantity;
        // this.dividend = dividend;
        // this.yield = yield;
    }

    public double buyCrypto(String name, double quantity) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.coingecko.com/api/v3/simple/price?ids=" + name + "&vs_currencies=usd"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if ("{}".equals(response.body()) == true) {
            System.out.println("Invalid crypto name.");
            return 0;
        }
        else{
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonObject topJson = jsonObject.getAsJsonObject(name);

            double price = topJson.get("usd").getAsDouble();

            if (price != 0) {
                // return price * quantity;
                setPrice();
                return price;
            } else {
                System.out.println("Invalid crypto name.");
                return 0;
            }
        }

    }
    //This function really just gets the price of the crypto, not actually buys it/updates the hashmap

    @Override
    public String toString() {
        return "Crytpo Name: " + cryptoname + ", Quantity: " + quantity + ", Price: " + price + "\n";
    }

    public void addQuantity(double quantity2) {
        this.quantity += quantity2;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice(){return price;}

    public String getCryptoname(){return cryptoname;}
    public double sellCrypto(String name, double quantity2) throws IOException, InterruptedException {
        double price = buyCrypto(name, quantity2);
        if (quantity2 > this.quantity) {
            System.out.println("You do not have enough crypto to sell that amount.");
            return 0;
        } else {
            this.quantity -= quantity2;
            price = Math.round(price * 100.0) / 100.0;
            return price * quantity2;
        }
    }

    public void setPrice() {
        this.price = Math.round(this.price * 100.0) / 100.0;
    }

}
