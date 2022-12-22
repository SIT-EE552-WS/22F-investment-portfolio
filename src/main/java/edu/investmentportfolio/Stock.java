package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;


public class Stock implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private String stockName ;
    private double price;
    private double quantity;
    static StockMarket stockMarket = new StockMarket();
    public Stock(){
    }

    public Stock(String stockName, double price, double quantity){
        this.stockName = stockName;
        this.price = price;
        this.quantity = quantity;
    }

    //helper to make http call for stock
    private static double getStockPrice(String name) throws IOException, InterruptedException {
        return stockMarket.getStockPrice(name);
    }



    // method to buy a stock
    public double buyStock(String name) throws IOException, InterruptedException {
        double stockPrice = getStockPrice(name);
        if (stockPrice == 0) {
            System.out.print("Invalid stock name.\n");
        }
        return setPriceStock(stockPrice);
    }

    // method to help display search for a stock
    public void viewStock(String name) throws IOException, InterruptedException {
        double stockPrice = getStockPrice(name);

        if (stockPrice == 0) {
            System.out.print("Invalid stock name.\n");
        } else{
            stockPrice = setPriceStock(stockPrice);
            System.out.print("Stock Name: " + name + ", Price: " + stockPrice + "\n");
        }
    }

    // method to sell a stock
    public double sellStock(String name, double stockQuantity) throws IOException, InterruptedException {
        double stockPrice = getStockPrice(name);
        if (stockQuantity <= this.quantity) {
            this.quantity -= stockQuantity;
            stockPrice = Math.round(stockPrice * 100.0) / 100.0;
            return stockPrice * stockQuantity;
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Stock Name: " + stockName + ", Quantity: " + quantity + ", Purchased Price: " + price + "\n";
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