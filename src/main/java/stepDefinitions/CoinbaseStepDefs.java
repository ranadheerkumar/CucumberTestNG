package stepDefinitions;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CoinbaseStepDefs {
    private static final String API_KEY = "your_api_key";
    private static final String API_SECRET = "your_api_secret";
    private static final String ENDPOINT_URL = "https://api.coinbase.com/v2/prices/BTC-USD/spot";

    private String value;

    @Given("I have the Coinbase API endpoint")
    public void setUp() {
        // Set up the endpoint URL
    }

    @When("I make a GET request to the endpoint")
    public void makeRequest() throws Exception {
        URL url = new URL(ENDPOINT_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("CB-ACCESS-KEY", API_KEY);
        con.setRequestProperty("CB-ACCESS-SIGN", generateSignature("GET", ENDPOINT_URL, "", System.currentTimeMillis() / 1000));
        con.setRequestProperty("CB-ACCESS-TIMESTAMP", Long.toString(System.currentTimeMillis() / 1000));
        con.setDoOutput(true);

        int status = con.getResponseCode();
        if (status != 200) {
            throw new IOException("Failed to make request: HTTP status code " + status);
        }

        // Read the response content
        value = extractValue(con.getInputStream());
    }

    @Then("I should retry the request until the value increases by {double}%")
    public void retryUntilValueIncreases(double percentage) throws Exception {
        double targetValue = Double.parseDouble(value) * (1 + percentage / 100);
        while (Double.parseDouble(value) < targetValue) {
            makeRequest();
        }
    }

    private static String generateSignature(String method, String endpoint, String body, long timestamp) throws Exception {
        String message = timestamp + method + endpoint + body;
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    private static String extractValue(InputStream inputStream) {
        // Parse the response content to extract the value.
        // Code to extract the value from the response goes here.
        return "0.0";
    }
}
