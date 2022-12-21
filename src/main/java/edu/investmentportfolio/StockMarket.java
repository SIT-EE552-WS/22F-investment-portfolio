package edu.investmentportfolio;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class StockMarket {
    private String apiKey;
    private Gson gson = new Gson();
    private HttpClient client = HttpClient.newBuilder().build();

    public StockMarket()  {
        Properties props = new Properties();
        try (InputStream inputStream = Stock.class.getClassLoader().getResourceAsStream("api.properties")){
            if (inputStream != null) {
                props.load(inputStream);
            }
            apiKey = props.getProperty("apiKey");
        }
        catch (IOException ex){
            throw new RuntimeException("Could not load API properties", ex);
        }
    }
     public double getStockPrice(String name) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + name + "&token=" + apiKey))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        //returns zero if the search doesn't work
        return jsonObject.get("c").getAsDouble();
    }
}
