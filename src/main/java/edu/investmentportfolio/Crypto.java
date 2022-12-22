package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


// Possible examples include bitcoin, ethereum, tether, binance coin,theta-token, etc.
// Anything under "id" on this page should work:
// https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd

public class Crypto implements Serializable, Instrument{
    @Serial
    private static final long serialVersionUID = 4L;
    private String cryptoName;
    private BigDecimal price;
    private BigDecimal quantity; // Fractions of crypto are supported in many exchanges, so we set quantity as BigDecimal
    static CryptoMarket cryptoMarket = new CryptoMarket();


    public Crypto() {
    }

    public Crypto(String cryptoName, BigDecimal price, BigDecimal quantity) {
        this.cryptoName = cryptoName;
        this.price = price;
        this.quantity = quantity;
    }

    //helper to make http call for crypto
    private static BigDecimal getCryptoPrice(String name) throws IOException, InterruptedException {
        return BigDecimal.valueOf(cryptoMarket.getCryptoPrice(name)).setScale(2, RoundingMode.HALF_EVEN);
    }

    //method to buy crypto
    public BigDecimal buyCrypto(String name) throws IOException, InterruptedException {
        BigDecimal cryptoPrice = getCryptoPrice(name);
        if (cryptoPrice == BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN)) {
            System.out.print("Invalid crypto name.\n");
        }
        return setPriceCrypto(cryptoPrice);
    }

    //method to help display search for crypto
    public void viewCrypto(String name) throws IOException, InterruptedException {
        BigDecimal cryptoPrice = getCryptoPrice(name);
        if (cryptoPrice == BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN)) {
            System.out.print("Invalid crypto name.\n");
        } else {
            //cryptoPrice = setPriceCrypto(cryptoPrice);
            System.out.print("Crypto Name: " + name + ", Price: " + cryptoPrice + "\n");
        }
    }

    //method to sell crypto
    public BigDecimal sellCrypto(String name, BigDecimal cryptoQuantity) throws IOException, InterruptedException {
        BigDecimal cryptoPrice = getCryptoPrice(name);
        if ((cryptoQuantity).compareTo(this.quantity) <= 0) {
            this.quantity = this.quantity.subtract(cryptoQuantity);
            return setPriceCrypto(cryptoPrice.multiply(cryptoQuantity));
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "Crypto Name: " + this.cryptoName + ", Quantity: " + this.quantity + ", Price: " + this.price + "\n";
    }

    public void addQuantity(BigDecimal quantity2) {
        this.quantity =quantity.add(quantity2);
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public BigDecimal getPrice(){return setPriceCrypto(this.price);}

    public String getCryptoName(){return this.cryptoName;}

    public BigDecimal setPriceCrypto(BigDecimal cryptoPrice) {
        return cryptoPrice.setScale(2, RoundingMode.HALF_EVEN);

    }
}
