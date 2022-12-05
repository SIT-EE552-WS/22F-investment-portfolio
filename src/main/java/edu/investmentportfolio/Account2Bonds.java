package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Account2Bonds<E> implements Serializable {
    private static final long serialVersionUID = 4L;
    private Map<String, E> portfolio = new HashMap<String, E>();
    private Cash cash = new Cash();
    private String firstName;
    private String lastName;

    public Account2Bonds() {
    }

    public Account2Bonds(String firstName, String lastName, double cash) {
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



    // TODO Start my code
    public void addBond(String name, double faceValue, double quantity) throws Exception {
        if (Bonds.setYear(name) !=0){
            Bonds temp = new Bonds();
            double price = faceValue;
            double amount = price * quantity;
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int val = Bonds.setYear(name)*365;
            cal.add(Calendar.DATE, val); //adds however many years
            int expMonth = cal.get(Calendar.MONTH)+1; //January starts at 0 for some reason so, I have to add 1
            int expYear = cal.get(Calendar.YEAR);

            if (price != 0) {
                if (this.cash.getBalance() < amount) {
                    System.out.println("You do not have enough money to buy that bond.");
                } else {
                    System.out.println(name + " bought at " + price);
                    this.cash.withdraw(amount);
                    if (portfolio.containsKey(name)) {
                        Bonds bond = (Bonds) portfolio.get(name);
                        bond.addQuantity(quantity);
                    } else {
                        Bonds bond = new Bonds(name, price, quantity, 8, quantity, expMonth, expYear);
                        portfolio.put(name, (E) bond);
                    }
                }
            }
        }
        else{
            System.out.println("Invalid Bond name.");
        }
    }

    public void viewBonds() {
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if ((entry.getKey() != "Cash")) {
                Bonds bond = (Bonds) entry.getValue();
                System.out.println(bond.toString());
            }
        }
    }

    public void sellBonds(String name, double faceValue, double quantity) throws IOException, InterruptedException {
        if (portfolio.containsKey(name)) {
            Bonds bond = (Bonds) portfolio.get(name);
            if (bond.getQuantity() < quantity) {
                System.out.println("You do not have enough bonds to sell that amount.");
            } else {
                double price = bond.sellBonds(name, faceValue, quantity);
                //double price = bond.sellBonds(name, quantity)
                this.cash.deposit(price);
                if (bond.getQuantity() == 0) {
                    portfolio.remove(name);
                }
            }
        } else {
            System.out.println("You do not have that bond.");
        }
    }
    // TODO Finish my code
    public void viewPortfolio() {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.println("Account Holder: " + this.firstName + " " + this.lastName + "\n");
        System.out.println("Cash Balance: " + this.cash.getBalance() + "\n");

        System.out.println("________________________________________________________");
        System.out.println("Current Bond Holdings: \n");
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getKey() != "Cash") {
                Bonds bond = (Bonds) entry.getValue();
                System.out.println(bond.toString());
            }
        }
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }
}
