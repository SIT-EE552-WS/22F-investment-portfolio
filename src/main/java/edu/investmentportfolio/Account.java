package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;



public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private final HashMap<String, Instrument> portfolio = new HashMap<>();
    private final Cash cash = new Cash();
    private String firstName;
    private String lastName;

    public Account() {
    }

    public Account(String firstName, String lastName, double cash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cash.deposit(cash);
        portfolio.put("Cash", this.cash);
    }

    public String getFirstName(){return this.firstName;}
    public String getLastName(){return this.lastName;}

    ////////////////// CASH //////////////////
    public void addCash(double cashAmount) {
        this.cash.deposit(cashAmount);
    }

    public void withdrawCash(double cashAmount) {
        this.cash.withdraw(cashAmount);
    }

    public void viewBalance() {
        System.out.println("Cash Balance: " + this.cash.getBalance());
    }

    ////////////////// STOCKS //////////////////
    public void addStock(String name, double quantity) throws IOException, InterruptedException {
        Stock desiredStock = new Stock();
        double price = desiredStock.buyStock(name);
        double amount = price * quantity;

        // if price is zero, then there is no stock with that name.
        if (price != 0) {
            if (this.cash.getBalance() < amount) {
                System.out.println("You do not have enough money to buy that stock.");
            } else {
                System.out.println("Transaction Successful");
                System.out.println(name + " bought at " + price + " for $" + amount);
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


    public void valueStocks() {
        double sum = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                double value = Math.round((stock.getQuantity() * stock.getPrice()) * 100.0) / 100.0;
                sum += value;
                System.out.println(stock.getStockName() + ": $" + value);
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        System.out.println("Total value = $" + sum);
    }

    public double getValueStocks() {
        double sum = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                double value = Math.round((stock.getQuantity() * stock.getPrice()) * 100.0) / 100.0;
                sum += value;
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        return sum;
    }

    public void sellStock(String name, double quantity) throws IOException, InterruptedException {
        name = name.toUpperCase();
        if (portfolio.containsKey(name)) {
            Stock stock = (Stock) portfolio.get(name);
            if (stock.getQuantity() < quantity) {
                System.out.println("You do not have enough stock to sell that amount.");
            } else {
                double price = stock.sellStock(name, quantity);
                System.out.println("Transaction Successful");
                System.out.println(name + " gained $" + price + ".");
                this.cash.deposit(price);
                if (stock.getQuantity() == 0) {
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
    public void addBond(String name, double faceValue, double quantity) throws IOException, InterruptedException {
        if (Bonds.setYear(name) != 0) {
            double amount = faceValue * quantity;

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int val = Bonds.setYear(name) * 365;
            cal.add(Calendar.DATE, val); // adds however many years
            int expMonth = cal.get(Calendar.MONTH) + 1; // January starts at 0 for some reason so, I have to add 1
            int expYear = cal.get(Calendar.YEAR);

            double couRate = Bonds.getBondInfo(name, 2);
            double yieldVal = Bonds.getBondInfo(name, 1);

            if (faceValue != 0) {
                if (this.cash.getBalance() < amount) {
                    System.out.println("You do not have enough money to buy that bond.");
                } else {
                    this.cash.withdraw(amount);
                    //now here
                    name = name +"year";
                    if (portfolio.containsKey(name)) {
                        Bonds bond = (Bonds) portfolio.get(name);
                        bond.addQuantity(quantity);
                    } else {
                        // was here
                        Bonds bond = new Bonds(name, faceValue, quantity, couRate, yieldVal, expMonth, expYear);
                        portfolio.put(name, bond);
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

    public void valueBonds() {
        double sum = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                sum += bond.getPresentValue();
                System.out.println(bond.getBondSymbol()+": $" + bond.getPresentValue());
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        System.out.println("Total present value = $" + sum);
    }


    public double getValueBonds() {
        double sum = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                sum += bond.getPresentValue();
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        return sum;
    }

    public void sellBond(String name, double quantity) {
        name = name + "year";
        if (portfolio.containsKey(name)) {
            Bonds bond = (Bonds) portfolio.get(name);
            if (bond.getQuantity() < quantity) {
                System.out.println("You do not have enough bonds to sell that amount.");
            } else {
                System.out.println("Transaction Successful");
                double price = bond.sellBonds(quantity);
                this.cash.deposit(price);
                if (bond.getQuantity() == 0) {
                    portfolio.remove(name);
                    System.out.println("No longer have a " + name + " bond.");
                }
            }
        } else {
            System.out.println("You do not have that bond.");
        }
    }

    ////////////////// Crypto //////////////////
    public void addCrypto(String name, double quantity) throws IOException, InterruptedException {
        Crypto desiredCrypto = new Crypto();
        double price = desiredCrypto.buyCrypto(name);
        double amount = price * quantity;

        if (price != 0) {
            if (this.cash.getBalance() < amount) {
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

    public void valueCrypto() {
        double sum = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                double value = Math.round((crypto.getQuantity()*crypto.getPrice()) * 100.0) / 100.0;
                sum += value;
                System.out.println(crypto.getCryptoName()+": $" + value);
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        System.out.println("Total value = $" + sum);
    }


    public double getValueCrypto() {
        double sum = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                double value = Math.round((crypto.getQuantity()*crypto.getPrice()) * 100.0) / 100.0;
                sum += value;
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        return sum;
    }

    public void sellCrypto(String name, double quantity) throws IOException, InterruptedException {
        if (portfolio.containsKey(name)) {
            Crypto crypto = (Crypto) portfolio.get(name);
            if (crypto.getQuantity() < quantity) {
                System.out.println("You do not have enough crypto to sell that amount.");
            } else {
                System.out.println("Transaction Successful");
                double price = crypto.sellCrypto(name, quantity);
                System.out.println(name + " gained $" + price + ".");
                this.cash.deposit(price);
                if (crypto.getQuantity() == 0) {
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


    public void valuePortfolio() {
        headerAccount();

        double sumStock = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock stock) {
                double value = Math.round((stock.getQuantity() * stock.getPrice()) * 100.0) / 100.0;
                sumStock += value;
                System.out.println(stock.getStockName()+": $" + value);
            }
        }
        if(sumStock == 0){
            System.out.println("No current stocks on profile.");
        }
        sumStock = Math.round(sumStock * 100.0) / 100.0;
        System.out.println("Total Stock Value = $" + sumStock);
        System.out.println("________________________________________________________");


        System.out.print("Current Bond Holdings: \n\n");
        double sumBond = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds bond) {
                sumBond += bond.getPresentValue();
                System.out.println(bond.getBondSymbol()+": $" + bond.getPresentValue() );
            }
        }
        if(sumBond == 0){
            System.out.println("No current bonds on profile.");
        }
        sumBond = Math.round(sumBond * 100.0) / 100.0;
        System.out.println("Total Bond Value = $" + sumBond);
        System.out.println("________________________________________________________");


        System.out.print("Current Crypto Holdings: \n\n");
        double sumCrypto = 0;
        for (Map.Entry<String, Instrument> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto crypto) {
                double value = Math.round((crypto.getQuantity()*crypto.getPrice()) * 100.0) / 100.0;
                sumCrypto += value;
                System.out.println(crypto.getCryptoName()+": $" + value);
            }
        }

        if(sumCrypto == 0){
            System.out.println("No current cryptos on profile.");
        }

        sumCrypto = Math.round(sumCrypto * 100.0) / 100.0;
        System.out.println("Total value = $" + sumCrypto);
        System.out.println("________________________________________________________");

        double total = sumStock + sumBond + sumCrypto;
        System.out.println("Total Portfolio Value = $" + (Math.round(total * 100.0) / 100.0));
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }

    //The graph function was based on code from the developer of x-chart
    //https://stackoverflow.com/questions/13662984/creating-pie-charts-programmatically

    public void holdingsGraph() {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Financial Instruments Graph").theme(Styler.ChartTheme.GGPlot2).build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);

        double r = getValueStocks();
        double g = getValueBonds();
        double b = getValueCrypto();
        chart.addSeries("Stocks", r);
        chart.addSeries("Bonds", g);
        chart.addSeries("Crypto", b);

        new SwingWrapper<>(chart).displayChart();

        //Uncomment the following line if you want to save the graph:
        //BitmapEncoder.saveBitmapWithDPI(chart, "./FinancialInstrumentsGraph", BitmapEncoder.BitmapFormat.PNG, 300)
    }
}