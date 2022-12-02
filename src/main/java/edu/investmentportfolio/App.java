package edu.investmentportfolio;

/**
 * Main Interface
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Investment Portfolio Simulator");

        Account user = new Account();

        user.addCash(1000);
        user.viewBalance();
        user.addStock("AAPL", 3.0);
        user.viewStocks();
        user.viewPortfolio();

    }
}
