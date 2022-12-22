package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDate;

import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import java.math.*;

import javax.swing.*;


@SuppressWarnings("DuplicatedCode")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private final HashMap<String, Instrument> portfolio = new HashMap<>();
    private final Cash cash = new Cash();
    private final String firstName;
    private final String lastName;

    public Account(String firstName, String lastName, BigDecimal cash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cash.deposit(cash);
        portfolio.put("Cash", this.cash);
    }

    public String getFirstName(){return this.firstName;}
    public String getLastName(){return this.lastName;}

    ////////////////// CASH //////////////////
    public void addCash(BigDecimal cashAmount) {
        this.cash.deposit(cashAmount);
    }

    public void withdrawCash(BigDecimal cashAmount) {
        this.cash.withdraw(cashAmount);
    }

    public void viewBalance() {
        System.out.println("Cash Balance: " + this.cash.getBalance());
    }

    ////////////////// STOCKS //////////////////
    public void addStock(String name, BigDecimal quantity) throws IOException, InterruptedException {
        Stock desiredStock = new Stock();
        BigDecimal price = desiredStock.buyStock(name);
        BigDecimal amount = price.multiply(quantity);

        // if price is zero, then there is no stock with that name.
        if (!(price.equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN)))) {
            if ((this.cash.getBalance()).compareTo(amount) < 0) {
                System.out.println("You do not have enough money to buy that stock.");
            } else {
                System.out.println("Transaction Successful");
                System.out.println(name + " bought at $" + price + " for $" + amount);
                this.cash.withdraw(amount);
                if (portfolio.containsKey(name)) {
                    Stock stock = (Stock) portfolio.get(name);
                    stock.addQuantity(quantity);
                } else {
                    Stock stock = new Stock(name, price, quantity);
                    portfolio.put(name, stock);
                }
            }
        }
    }

    public void viewStocks() {
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                System.out.print(stock);
            }
        }
    }


    public void valueStocks() throws IOException, InterruptedException {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                BigDecimal value = (stock.getQuantity()).multiply(stock.buyStock(stock.getStockName()));
                sum =sum.add(value);
                System.out.println(stock.getStockName() + ": $" + value);
            }
        }
        sum = sum.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total value = $" + sum);
    }

    public BigDecimal getValueStocks() throws IOException, InterruptedException {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                BigDecimal value = (stock.getQuantity()).multiply(stock.buyStock(stock.getStockName()));
                sum = sum.add(value);
            }
        }
        sum = sum.setScale(2, RoundingMode.HALF_EVEN);
        return sum;
    }

    public void sellStock(String name, BigDecimal quantity) throws IOException, InterruptedException {
        name = name.toUpperCase();
        if (portfolio.containsKey(name)) {
            Stock stock = (Stock) portfolio.get(name);

            if ((stock.getQuantity()).compareTo(quantity) < 0) {
                System.out.println("You do not have enough stock to sell that amount.");
            } else {
                BigDecimal price = stock.sellStock(name, quantity);
                System.out.println("Transaction Successful");
                this.cash.deposit(price);
                if (stock.getQuantity().setScale(0, RoundingMode.HALF_EVEN).equals(BigDecimal.ZERO)) {
                    portfolio.remove(name);
                    System.out.println("No longer have any " + name + " stocks.");
                }
            }
        } else {
            System.out.println("You do not have that stock.");
        }
    }

    public void searchStock(String name) throws IOException, InterruptedException {
        Stock stock = new Stock();
        stock.viewStock(name);
    }

    ////////////////// Bonds //////////////////
    public void addBond(int name, BigDecimal faceValue, BigDecimal quantity) throws IOException, InterruptedException {

        if (Bonds.setYear(name) != 0) {
            BigDecimal amount = faceValue.multiply(quantity);
            LocalDate localDate = LocalDate.now(); // sets date to today
            localDate = localDate.plusYears(name); // adds however many years the bond lasts
            int expMonth = localDate.getMonthValue();
            int expYear = localDate.getYear();

            BigDecimal couRate = Bonds.getBondInfoCouponRate(name);
            BigDecimal yieldVal = Bonds.getBondInfoBondYield(name);

            if (!faceValue.equals(BigDecimal.ZERO)) {

                if ((this.cash.getBalance().compareTo(amount) < 0)) {
                    System.out.println("You do not have enough money to buy that bond.");
                } else {
                    this.cash.withdraw(amount);
                    String finalName = name +"year";
                    if (portfolio.containsKey(finalName)) {
                        Bonds bond = (Bonds) portfolio.get(finalName);
                        bond.addQuantity(quantity);
                    } else {
                        Bonds bond = new Bonds(finalName, faceValue, quantity, couRate, yieldVal, expMonth, expYear);
                        portfolio.put(finalName, bond);
                    }
                    System.out.println("Transaction Successful");
                }
            }
        } else {
            System.out.println("Invalid Bond name.");
        }
    }

    public void viewBonds() {
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                System.out.print(bond);
            }
        }
    }

    // This function prints out the value of each bond and the final sum
    public void valueBonds() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                sum = sum.add(bond.getPresentValue());
                System.out.println(bond.getBondSymbol()+": $" + bond.getPresentValue());
            }
        }
        sum = sum.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total present value = $" + sum);
    }

    // This function calculates the value of all bonds and returns the final sum without printing the value of each bond or the final sum
    public BigDecimal getValueBonds() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                sum =sum.add(bond.getPresentValue());
            }
        }
        sum = sum.setScale(2, RoundingMode.HALF_EVEN);
        return sum;
    }

    public void sellBond(int name, BigDecimal quantity) {
        String nameString =  name + "year";
        if (portfolio.containsKey(nameString)) {
            Bonds bond = (Bonds) portfolio.get(nameString);

            if ((bond.getQuantity().compareTo(quantity) < 0)) {
                System.out.println("You do not have enough bonds to sell that amount.");
            } else {
                System.out.println("Transaction Successful");
                BigDecimal price = bond.sellBonds(quantity);
                this.cash.deposit(price);
                if (bond.getQuantity().setScale(0, RoundingMode.HALF_EVEN).equals(BigDecimal.ZERO)) {
                    portfolio.remove(nameString);
                    System.out.println("No longer have a " + nameString + " bond.");
                }
            }
        } else {
            System.out.println("You do not have that bond.");
        }
    }

    ////////////////// Crypto //////////////////
    public void addCrypto(String name, BigDecimal quantity) throws IOException, InterruptedException {
        Crypto desiredCrypto = new Crypto();
        BigDecimal price = desiredCrypto.buyCrypto(name);
        BigDecimal amount = price.multiply(quantity);

        if (!(price.equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN)))) {

            if ((this.cash.getBalance().compareTo(amount) < 0)) {
                System.out.println("You do not have enough money to buy that Crypto.");
            } else {
                System.out.println("Transaction Successful");
                System.out.println(name + " bought at " + price + " for $" + amount);
                this.cash.withdraw(amount);
                if (portfolio.containsKey(name)) {
                    Crypto crypto = (Crypto) portfolio.get(name);
                    crypto.addQuantity(quantity);
                } else {
                    Crypto crypto = new Crypto(name, price, quantity);
                    portfolio.put(name, crypto);
                }
            }
        }
    }

    public void viewCrypto() {
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                System.out.print(crypto);
            }
        }
    }

    public void valueCrypto() throws IOException, InterruptedException {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                BigDecimal value = (crypto.getQuantity()).multiply(crypto.buyCrypto(crypto.getCryptoName()));
                sum =sum.add(value);
                System.out.println(crypto.getCryptoName()+": $" + value);
            }
        }
        sum = sum.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total value = $" + sum);
    }


    public BigDecimal getValueCrypto() throws IOException, InterruptedException {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                BigDecimal value = (crypto.getQuantity()).multiply(crypto.buyCrypto(crypto.getCryptoName()));
                sum =sum.add(value);
            }
        }
        sum = sum.setScale(2, RoundingMode.HALF_EVEN);
        return sum;
    }

    public void sellCrypto(String name, BigDecimal quantity) throws IOException, InterruptedException {
        if (portfolio.containsKey(name)) {
            Crypto crypto = (Crypto) portfolio.get(name);

            if ((crypto.getQuantity().compareTo(quantity) < 0)) {
                System.out.println("You do not have enough crypto to sell that amount.");
            } else {
                System.out.println("Transaction Successful");
                BigDecimal price = crypto.sellCrypto(name, quantity);
                this.cash.deposit(price);
                if (crypto.getQuantity().setScale(0, RoundingMode.HALF_EVEN).equals(BigDecimal.ZERO)) {
                    portfolio.remove(name);
                    System.out.println("No longer have any " + name + " crypto.");
                }
            }
        } else {
            System.out.println("You do not have that crypto.");
        }
    }

    public void searchCrypto(String name) throws IOException, InterruptedException {
        Crypto crypto = new Crypto();
        crypto.viewCrypto(name);
    }

    ////////////////// PORTFOLIO //////////////////
    private void headerAccount() {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.print("Account Holder: " + this.firstName + " " + this.lastName + "\n");
        System.out.print("Cash Balance: " + this.cash.getBalance() + "\n");

        System.out.println("________________________________________________________");
        System.out.print("Current Stock Holdings: \n\n");
    }

    public void viewPortfolio() {
        headerAccount();

        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                System.out.println(stock);
            }
        }
        System.out.println("________________________________________________________");
        System.out.print("Current Bond Holdings: \n\n");
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                System.out.println(bond);
            }
        }
        System.out.println("________________________________________________________");
        System.out.print("Current Crypto Holdings: \n\n");
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                System.out.println(crypto);
            }
        }
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }


    public void valuePortfolio() throws IOException, InterruptedException {
        headerAccount();
        BigDecimal sumStock = BigDecimal.valueOf(0);
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                BigDecimal value = (stock.getQuantity()).multiply(stock.buyStock(stock.getStockName()));
                sumStock = sumStock.add(value);
                System.out.println(stock.getStockName()+": $" + value);
            }
        }
        if(sumStock == BigDecimal.ZERO){
            System.out.println("No current stocks on profile.");
        }
        sumStock = sumStock.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total Stock Value = $" + sumStock);

        System.out.println("________________________________________________________");


        System.out.print("Current Bond Holdings: \n\n");
        BigDecimal sumBond = BigDecimal.valueOf(0);
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                sumBond =sumBond.add(bond.getPresentValue());
                System.out.println(bond.getBondSymbol()+": $" + bond.getPresentValue() );
            }
        }
        if(sumBond == BigDecimal.ZERO){
            System.out.println("No current bonds on profile.");
        }
        sumBond = sumBond.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total Bond Value = $" + sumBond);
        System.out.println("________________________________________________________");


        System.out.print("Current Crypto Holdings: \n\n");
        BigDecimal sumCrypto = BigDecimal.ZERO;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                BigDecimal value = (crypto.getQuantity()).multiply(crypto.buyCrypto(crypto.getCryptoName()));                sumCrypto = sumCrypto.add(value);
                System.out.println(crypto.getCryptoName()+": $" + value);
            }
        }

        if(sumCrypto == BigDecimal.ZERO){
            System.out.println("No current cryptos on profile.");
        }

        sumCrypto = sumCrypto.setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total value = $" + sumCrypto);
        System.out.println("________________________________________________________");

        BigDecimal total = sumStock.add(sumBond.add(sumCrypto));
        System.out.println("Total Portfolio Value = $" + total.setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }

    //The graph function was based on code from the developer of x-chart
    //https://stackoverflow.com/questions/13662984/creating-pie-charts-programmatically

    public void holdingsGraph() throws IOException, InterruptedException {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Financial Instruments Graph").theme(Styler.ChartTheme.GGPlot2).build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);

        BigDecimal r = getValueStocks();
        BigDecimal g = getValueBonds();
        BigDecimal b = getValueCrypto();
        chart.addSeries("Stocks", r);
        chart.addSeries("Bonds", g);
        chart.addSeries("Crypto", b);

        new SwingWrapper<>(chart).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);;

        //Uncomment the following line if you want to save the graph:
        //BitmapEncoder.saveBitmapWithDPI(chart, "./FinancialInstrumentsGraph", BitmapEncoder.BitmapFormat.PNG, 300)
    }
}