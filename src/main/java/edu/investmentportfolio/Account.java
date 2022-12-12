package edu.investmentportfolio;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;



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
            if (entry.getValue() instanceof Stock) {
                Stock stock = (Stock) entry.getValue();
                System.out.println(stock.toString());
            }
        }
    }
    public void valueStocks() {
        double sum=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock) {
                Stock stock = (Stock) entry.getValue();
                sum += stock.getQuantity()*stock.getPrice();
                System.out.println(stock.getStockname()+": $" + stock.getQuantity()*stock.getPrice());
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        System.out.println("Total value = $" + sum);
    }
    public double getValueStocks() {
        double sum=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock) {
                Stock stock = (Stock) entry.getValue();
                sum += stock.getQuantity()*stock.getPrice();
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        return sum;
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

    ////////////////// Bonds //////////////////
    public void addBond(String name, double faceValue, double quantity) throws Exception {
        if (Bonds.setYear(name) != 0) {
            // Bonds temp = new Bonds();
            double price = faceValue;
            double amount = price * quantity;
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int val = Bonds.setYear(name) * 365;
            cal.add(Calendar.DATE, val); // adds however many years
            int expMonth = cal.get(Calendar.MONTH) + 1; // January starts at 0 for some reason so, I have to add 1
            int expYear = cal.get(Calendar.YEAR);
            double couRate = Bonds.getBondInfo(name, faceValue,  quantity, 2);
            double yieldVal = Bonds.getBondInfo(name, faceValue,  quantity, 1);

            if (price != 0) {
                if (this.cash.getBalance() < amount) {
                    System.out.println("You do not have enough money to buy that bond.");
                } else {
                    System.out.println(name + " bought at " + price);
                    this.cash.withdraw(amount);
                    //now here
                    name = name+"("+price+")";
                    if (portfolio.containsKey(name)) {
                        Bonds bond = (Bonds) portfolio.get(name);
                        bond.addQuantity(quantity);
                    } else {
                        // was here
                        Bonds bond = new Bonds(name, price, quantity, couRate, yieldVal, expMonth, expYear);
                        portfolio.put(name, (E) bond);
                    }
                }
            }
        } else {
            System.out.println("Invalid Bond name.");
        }
    }

    public void viewBonds() {
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds) {
                Bonds bond = (Bonds) entry.getValue();
                System.out.println(bond.toString());
            }
        }
    }

    public void valueBonds() {
        double sum=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds) {
                Bonds bond = (Bonds) entry.getValue();
                sum += bond.getPresentValue();
                System.out.println(bond.getBondSymbol()+": $" + bond.getPresentValue());
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        System.out.println("Total present value = $" + sum);
    }
    public double getValueBonds() {
        double sum=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds) {
                Bonds bond = (Bonds) entry.getValue();
                sum += bond.getPresentValue();
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        return sum;
    }

    public void sellBond(String name, double quantity) throws IOException, InterruptedException {
        if (portfolio.containsKey(name)) {
            Bonds bond = (Bonds) portfolio.get(name);
            if (bond.getQuantity() < quantity) {
                System.out.println("You do not have enough bonds to sell that amount.");
            } else {
                double price = bond.sellBonds(name, quantity);
                // double price = bond.sellBonds(name, quantity)
                this.cash.deposit(price);
                if (bond.getQuantity() == 0) {
                    portfolio.remove(name);
                }
            }
        } else {
            System.out.println("You do not have that bond.");
        }
    }

    ////////////////// Crypto //////////////////
    public void addCrypto(String name, double quantity) throws Exception {
        Crypto temp = new Crypto();
        double price = temp.buyCrypto(name, quantity);
        double amount = price * quantity;
        if (price != 0) {
            if (this.cash.getBalance() < amount) {
                System.out.println("You do not have enough money to buy that Crypto.");
            } else {
                System.out.println(name + " bought at " + price);
                this.cash.withdraw(amount);
                if (portfolio.containsKey(name)) {
                    Crypto crypto = (Crypto) portfolio.get(name);
                    crypto.addQuantity(quantity);
                } else {
                    Crypto crypto = new Crypto(name, price, quantity);
                    portfolio.put(name, (E) crypto);
                }
            }
        }
    }

    public void viewCrypto() {
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto) {
                Crypto crypto = (Crypto) entry.getValue();
                System.out.println(crypto.toString());
            }
        }
    }
    public void valueCrypto() {
        double sum=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto) {
                Crypto crypto = (Crypto) entry.getValue();
                sum += crypto.getQuantity()*crypto.getPrice();
                System.out.println(crypto.getCryptoname()+": $" + (crypto.getQuantity()*crypto.getPrice()));
            }
        }
        sum = Math.round(sum * 100.0) / 100.0;
        System.out.println("Total value = $" + sum);
    }
    public double getValueCrypto() {
        double sum=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto) {
                Crypto crypto = (Crypto) entry.getValue();
                sum += crypto.getQuantity()*crypto.getPrice();
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
                double price = crypto.sellCrypto(name, quantity);
                this.cash.deposit(price);
                if (crypto.getQuantity() == 0) {
                    portfolio.remove(name);
                }
            }
        } else {
            System.out.println("You do not have that crypto.");
        }
    }


    ////////////////// PORTFOLIO //////////////////
    public void viewPortfolio() {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.println("Account Holder: " + this.firstName + " " + this.lastName + "\n");
        System.out.println("Cash Balance: " + this.cash.getBalance() + "\n");

        System.out.println("________________________________________________________");
        System.out.println("Current Stock Holdings: \n");
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock) {
                Stock stock = (Stock) entry.getValue();
                System.out.println(stock.toString());
            }
        }
        System.out.println("________________________________________________________");
        System.out.println("Current Bond Holdings: \n");
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds) {
                Bonds bond = (Bonds) entry.getValue();
                System.out.println(bond.toString());
            }
        }

        System.out.println("________________________________________________________");
        System.out.println("Current Crypto Holdings: \n");
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto) {
                Crypto crypto = (Crypto) entry.getValue();
                System.out.println(crypto.toString());
            }
        }
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }

    public void valuePortfolio() {
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        System.out.println("Account Holder: " + this.firstName + " " + this.lastName + "\n");
        System.out.println("Cash Balance: " + this.cash.getBalance() + "\n");

        System.out.println("________________________________________________________");
        System.out.println("Current Stock Holdings: \n");
        double sumStock=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Stock) {
                Stock stock = (Stock) entry.getValue();
                sumStock += stock.getQuantity()*stock.getPrice();
                System.out.println(stock.getStockname()+": $" + stock.getQuantity()*stock.getPrice());
            }
        }
        sumStock = Math.round(sumStock * 100.0) / 100.0;
        System.out.println("Total Stock Value = $" + sumStock);
        System.out.println("________________________________________________________");
        System.out.println("Current Bond Holdings: \n");
        double sumBond=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Bonds) {
                Bonds bond = (Bonds) entry.getValue();
                sumBond += bond.getPresentValue();
                System.out.println(bond.getBondSymbol()+": $" + bond.getPresentValue());
            }
        }
        sumBond = Math.round(sumBond * 100.0) / 100.0;
        System.out.println("Total Bond Value = $" + sumBond);
        System.out.println("________________________________________________________");
        double sumCrypto=0;
        for (Map.Entry<String, E> entry : portfolio.entrySet()) {
            if (entry.getValue() instanceof Crypto) {
                Crypto crypto = (Crypto) entry.getValue();
                sumCrypto += crypto.getQuantity()*crypto.getPrice();
                System.out.println(crypto.getCryptoname()+": $" + crypto.getQuantity()*crypto.getPrice());
            }
        }
        sumCrypto = Math.round(sumCrypto * 100.0) / 100.0;
        System.out.println("Total value = $" + sumCrypto);
        System.out.println("________________________________________________________");
        System.out.println("Total Portfolio Value = $" + (sumStock+sumBond+sumCrypto));
        System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
    }

    //The graph function was based on code from the developer of xchart
    //https://stackoverflow.com/questions/13662984/creating-pie-charts-programmatically

    public void holdingsGraph() throws IOException {
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

        new SwingWrapper(chart).displayChart();

        //Uncomment the following line if you want to save the graph:
        //BitmapEncoder.saveBitmapWithDPI(chart, "./FinancialInstrumentsGraph", BitmapEncoder.BitmapFormat.PNG, 300);
    }
}