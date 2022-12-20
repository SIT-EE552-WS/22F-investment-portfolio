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


public class CryptoMarket {
    private Gson gson = new Gson();
    private HttpClient client = HttpClient.newBuilder().build();

    public CryptoMarket()  {
    }

    public double getCryptoPrice(String name) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.coingecko.com/api/v3/simple/price?ids=" + name + "&vs_currencies=usd"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if ("{}".equals(response.body())) {
            return 0;
        }
        else {
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonObject topJson = jsonObject.getAsJsonObject(name);

            return topJson.get("usd").getAsDouble();
        }
    }
}
