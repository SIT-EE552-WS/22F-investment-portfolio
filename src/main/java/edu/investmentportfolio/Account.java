package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Account<E> implements Serializable {
    private static final long serialVersionUID = 4L;
    private Map<String, E> portfolio = new HashMap<String, E>();
    private Cash cash = new Cash();
    private String firstName;
    private String lastName;

    public Account() {
    }

    public Account(String firstName, String lastName, double cash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cash.deposit(cash);
        portfolio.put("Cash", (E) this.cash);
    }

    public void addCash(double cashAmount) {
        this.cash.deposit(cashAmount);
    }

    public void withdrawCash(double cashAmount) {
        this.cash.withdraw(cashAmount);
    }

    public void viewBalance() {
        System.out.println("Cash Balance: " + this.cash.getBalance());
    }

    public void addStock(String name, double quantity) throws Exception {
        Stock temp = new Stock();
        double price = temp.buyStock(name, quantity);
        double amount = price * quantity;
        if (price != 0) {
            if (this.cash.getBalance() < amount) {
                System.out.println("You do not have enough money to buy that stock.");
            } else {
                System.out.println(name + " bought at " + price);
                this.cash.withdraw(amount);
                if (portfolio.containsKey(name)) {
                    Stock stock = (Stock) portfolio.get(name);
                    stock.addQuantity(quantity);
                } else {
                    Stock stock = new Stock(name, price, quantity);
                    portfolio.put(name, (E) stock);
                }
            }
        }
    }

    public void viewStocks() {
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getKey() != "Cash") {
                Stock stock = (Stock) entry.getValue();
                System.out.println(stock.toString());
            }
        }
    }

    public void sellStock(String name, double quantity) throws IOException, InterruptedException {
        if (portfolio.containsKey(name)) {
            Stock stock = (Stock) portfolio.get(name);
            if (stock.getQuantity() < quantity) {
                System.out.println("You do not have enough stock to sell that amount.");
            } else {
                double price = stock.sellStock(name, quantity);
                this.cash.deposit(price);
                if (stock.getQuantity() == 0) {
                    portfolio.remove(name);
                }
            }
        } else {
            System.out.println("You do not have that stock.");
        }
    }

    public void viewPortfolio() {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.println("Account Holder: " + this.firstName + " " + this.lastName + "\n");
        System.out.println("Cash Balance: " + this.cash.getBalance() + "\n");

        System.out.println("________________________________________________________");
        System.out.println("Current Stock Holdings: \n");
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getKey() != "Cash") {
                Stock stock = (Stock) entry.getValue();
                System.out.println(stock.toString());
            }
        }
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }
}