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

//Stock (price, dividend, yield) 
public class Stock implements Serializable {
    private static final long serialVersionUID = 4L;
    private String stockname;
    private double price;
    private double quantity;
    // private double dividend;
    // private double yield;

    public Stock() {
    }

    public Stock(String stockname, double price, double quantity) {
        this.stockname = stockname;
        this.price = price;
        this.quantity = quantity;
        // this.dividend = dividend;
        // this.yield = yield;
    }

    public double buyStock(String name, double quantity) throws IOException, InterruptedException {
        Properties props = new Properties();
        InputStream inputStream = Stock.class.getClassLoader().getResourceAsStream("api.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }
        final String apiKey = props.getProperty("apiKey");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + name + "&token=" + apiKey))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double price = jsonObject.get("c").getAsDouble();

        if (price != 0) {
            return this.price * this.quantity;
        } else {
            System.out.println("Invalid stock name.");
            return 0;
        }
    }

    public String viewStock(String name, double quantity) throws IOException, InterruptedException {
        Properties props = new Properties();
        InputStream inputStream = Stock.class.getClassLoader().getResourceAsStream("api.properties");
        if (inputStream != null) {
            props.load(inputStream);
        }
        final String apiKey = props.getProperty("apiKey");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + name + "&token=" + apiKey))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double price = jsonObject.get("c").getAsDouble();

        if (price == 0) {
            return "Invalid stock name.";
        }

        return "Stock Name: " + name + ", Quantity: " + quantity + ", Price: " + price + "\n";
    }

    @Override
    public String toString() {
        return "Stock Name: " + stockname + ", Quantity: " + quantity + ", Price: " + price + "\n";
    }

    public void addQuantity(double quantity2) {
        this.quantity += quantity2;
    }

    public double getQuantity() {
        return quantity;
    }

    public double sellStock(double quantity2) {
        if (quantity2 > this.quantity) {
            System.out.println("You do not have enough stock to sell that amount.");
            return 0;
        } else {
            this.quantity -= quantity2;
            return this.price * quantity2;
        }
    }

}