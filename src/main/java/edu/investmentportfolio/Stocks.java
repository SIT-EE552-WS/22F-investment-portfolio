package edu.investmentportfolio;

import java.io.Serializable;

//Stock (price, dividend, yield) 
public class Stocks implements Serializable {
    private String companyName;
    private double price;
    private double dividend;
    private double yield;
    private double purchasedDate;
    private double purchasedPrice;
    private double purchasedQuantity;

    public Stocks(String companyName, double purchasedDate, double purchasedPrice, double purchasedQuantity) {
        this.companyName = companyName;
        this.purchasedDate = purchasedDate;
        this.purchasedPrice = purchasedPrice;
        this.purchasedQuantity = purchasedQuantity;
    }

    public void getStocks() {
        // api calls to search/get stock info

    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPurchasedDate() {
        return purchasedDate;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public double getPurchasedQuantity() {
        return purchasedQuantity;
    }
}