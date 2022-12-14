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

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Stock implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private String stockName ;
    private double price;
    private double quantity;

    public Stock() {
    }

    public Stock(String stockName, double price, double quantity) {
        this.stockName = stockName;
        this.price = price;
        this.quantity = quantity;
    }

    //helper to make http call for stock
    private static double getStockPrice(String name) throws IOException, InterruptedException {
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

        //returns zero if the search doesn't work
        return jsonObject.get("c").getAsDouble();
    }

    // method to buy a stock
    public double buyStock(String name) throws IOException, InterruptedException {
        double stockPrice = getStockPrice(name);
        return setPriceStock(stockPrice);
    }

    // method to view a stock
    public String viewStock(String name) throws IOException, InterruptedException {
        double stockPrice = getStockPrice(name);

        if (stockPrice == 0) {
            return "Invalid stock name.";
        }
        stockPrice = setPriceStock(stockPrice);
        return "Stock Name: " + name + ", Price: " + stockPrice + "\n";
    }

    // method to sell a stock
    public double sellStock(String name, double stockQuantity) throws IOException, InterruptedException {
        double stockPrice = getStockPrice(name);
        if (stockQuantity < this.quantity) {
            this.quantity -= stockQuantity;
            stockPrice = Math.round(stockPrice * 100.0) / 100.0;
            return stockPrice * stockQuantity;
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Stock Name: " + stockName + ", Quantity: " + quantity + ", Price: " + price + "\n";
    }

    public void addQuantity(double stockQuantity) {
        this.quantity += stockQuantity;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public double getPrice(){return setPriceStock(this.price);}

    public String getStockName(){return this.stockName;}

    public double setPriceStock(double stockPrice) {
        return Math.round(stockPrice * 100.0) / 100.0;
    }

}