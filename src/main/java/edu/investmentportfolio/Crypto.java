package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;


// Possible examples include bitcoin, ethereum, tether, binance coin,theta-token, etc.
// Anything under "id" on this page should work:
// https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd

public class Crypto implements Serializable, Instrument{
    @Serial
    private static final long serialVersionUID = 4L;
    private String cryptoName;
    private double price;
    private double quantity;
    static CryptoMarket cryptoMarket = new CryptoMarket();


    public Crypto() {
    }

    public Crypto(String cryptoName, double price, double quantity) {
        this.cryptoName = cryptoName;
        this.price = price;
        this.quantity = quantity;
    }

    //helper to make http call for crypto
    private static double getCryptoPrice(String name) throws IOException, InterruptedException {
        return cryptoMarket.getCryptoPrice(name);
    }

    //method to buy crypto
    public double buyCrypto(String name) throws IOException, InterruptedException {
        double cryptoPrice = getCryptoPrice(name);
        return setPriceCrypto(cryptoPrice);
    }

    //method to help display search for crypto
    public void viewCrypto(String name) throws IOException, InterruptedException {
        double cryptoPrice = getCryptoPrice(name);
        if (cryptoPrice == 0) {
            System.out.print("Invalid crypto name.\n");
        } else {
            cryptoPrice = setPriceCrypto(cryptoPrice);
            System.out.print("Crypto Name: " + name + ", Price: " + cryptoPrice + "\n");
        }
    }

    //method to sell crypto
    public double sellCrypto(String name, double cryptoQuantity) throws IOException, InterruptedException {
        double cryptoPrice = getCryptoPrice(name);
        if (cryptoQuantity <= this.quantity) {
            this.quantity -= cryptoQuantity;
            cryptoPrice = Math.round(cryptoPrice * 100.0) / 100.0;
            return setPriceCrypto(cryptoPrice * cryptoQuantity);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Crypto Name: " + this.cryptoName + ", Quantity: " + this.quantity + ", Price: " + this.price + "\n";
    }

    public void addQuantity(double quantity2) {this.quantity += quantity2;}

    public double getQuantity() {
        return this.quantity;
    }

    public double getPrice(){return setPriceCrypto(this.price);}

    public String getCryptoName(){return this.cryptoName;}

    public double setPriceCrypto(double cryptoPrice) {
        return Math.round(cryptoPrice * 100.0) / 100.0;

    }
}
