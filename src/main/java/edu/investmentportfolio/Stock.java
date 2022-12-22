package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Stock implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private String stockName ;
    private BigDecimal price;
    private BigDecimal quantity; // Fractional shares are supported in many exchanges, so we set quantity as BigDecimal
    static StockMarket stockMarket = new StockMarket();
    public Stock(){
    }

    public Stock(String stockName, BigDecimal price, BigDecimal quantity){
        this.stockName = stockName;
        this.price = price;
        this.quantity = quantity;
    }

    //helper to make http call for stock
    private static BigDecimal getStockPrice(String name) throws IOException, InterruptedException {
        return BigDecimal.valueOf(stockMarket.getStockPrice(name)).setScale(2, RoundingMode.HALF_EVEN);
    }



    // method to buy a stock
    public BigDecimal buyStock(String name) throws IOException, InterruptedException {
        BigDecimal stockPrice = getStockPrice(name);
        if (stockPrice.equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))) {
            System.out.print("Invalid stock name.\n");
        }
        return setPriceStock(stockPrice);
    }

    // method to help display search for a stock
    public void viewStock(String name) throws IOException, InterruptedException {
        BigDecimal stockPrice = getStockPrice(name);

        if (stockPrice.equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))) {
            System.out.print("Invalid stock name.\n");
        } else{
            stockPrice = setPriceStock(stockPrice);
            System.out.print("Stock Name: " + name + ", Price: " + stockPrice + "\n");
        }
    }

    // method to sell a stock
    public BigDecimal sellStock(String name, BigDecimal stockQuantity) throws IOException, InterruptedException {
        BigDecimal stockPrice = getStockPrice(name);

        if ((stockQuantity.compareTo(this.quantity) <= 0)) {
            this.quantity = this.quantity.subtract(stockQuantity);
            return stockPrice.multiply(stockQuantity);
        }
        return BigDecimal.ZERO;
    }
    @Override
    public String toString() {
        return "Stock Name: " + stockName + ", Quantity: " + quantity + ", Purchased Price: " + price + "\n";
    }

    public void addQuantity(BigDecimal stockQuantity) {this.quantity = this.quantity.add(stockQuantity);}

    public BigDecimal getQuantity() {return this.quantity;}

    public BigDecimal getPrice(){return setPriceStock(this.price);}

    public String getStockName(){return this.stockName;}

    public BigDecimal setPriceStock(BigDecimal stockPrice) {
        return (stockPrice.setScale(2, RoundingMode.HALF_EVEN));
    }

}