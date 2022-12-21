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
import org.json.JSONArray;


public class BondMarket {
    private Gson gson = new Gson();
    private HttpClient client = HttpClient.newBuilder().build();
    private Properties props = new Properties();

    public BondMarket()  {

        try (InputStream inputStream = Stock.class.getClassLoader().getResourceAsStream("BondsClassifier.properties")){
            if (inputStream != null) {
                props.load(inputStream);
            }

        }
        catch (IOException ex){
            throw new RuntimeException("Could not load API properties", ex);
        }
    }
    public JSONArray getJsonArrayBond(int name) throws IOException, InterruptedException {
        String combine = "cusip" + name;
        String cusip = props.getProperty(combine);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://www.treasurydirect.gov/TA_WS/securities/search?cusip=" + cusip + "&format=json"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONArray(response.body());
    }

}
