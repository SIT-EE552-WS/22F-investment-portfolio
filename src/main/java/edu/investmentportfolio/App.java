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
        user.addStock("AAPL", 3);
        user.sellStock("AAPL", 5);
        user.viewPortfolio();
        user.addStock("MSFT", 1);
        user.viewPortfolio();
        user.addStock("MSFT", 10);
    }
}
