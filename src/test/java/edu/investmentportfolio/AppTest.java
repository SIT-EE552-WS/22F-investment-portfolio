package edu.investmentportfolio;

/**
 * Unit test for simple App.
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

public class AppTest {
    @Test
    public void test_buyStock() throws IOException, InterruptedException {
        Stock stock = new Stock();
        Assertions.assertNotEquals(0.0,stock.buyStock("AAPL")); //It should be Appls's current stock price which could vary. At the time this test was created it was 135
    }
    @Test
    public void test_getValueStocks() throws IOException, InterruptedException {
        Account account = new Account("Test", "Case", 1000);
        account.addCash(1000);
        account.addStock("MSFT",0);
        Assertions.assertEquals(0.0,account.getValueStocks()); // Buying 0 stocks should return 0
        account.addStock("MSFT",2);
        Assertions.assertNotEquals(0.0,account.getValueStocks()); // Buying 2 stocks should return a number other than 0
        account.sellStock("MSFT",2);
        Assertions.assertEquals(0.0,account.getValueStocks()); // Selling 2 stocks should return 0
    }

    @Test
    public void test_buyCrypto() throws IOException, InterruptedException {
        Crypto crypto = new Crypto();
        Assertions.assertNotEquals(0.0,crypto.buyCrypto("bitcoin")); //It should be return bitcoin's current price (16882.55 when test was first performed)
    }
    @Test
    public void test_getValueCrypto() throws IOException, InterruptedException {
        Account account = new Account("Test", "Case", 100000);
        account.addCrypto("ethereum",0);
        Assertions.assertEquals(0.0,account.getValueCrypto()); // Buying 0 crypto should return 0
        account.addCrypto("ethereum",2);
        Assertions.assertNotEquals(0.0,account.getValueCrypto()); // Buying 2 crypto should return a number other than 0
        account.sellCrypto("ethereum",2);
        Assertions.assertEquals(0.0,account.getValueCrypto()); // Selling 2 crypto should return 0
    }
    @Test
    public void test_getValueBonds() throws IOException, InterruptedException {
        Account account = new Account("Test", "Case", 100000);
        account.addBond(10,1000,0);
        Assertions.assertEquals(0.0,account.getValueBonds()); // Buying 0 bonds should return 0
        account.addBond(10,1000,1);
        Assertions.assertEquals(1029.35,account.getValueBonds()); // Value of bonds is based on a constant equation and returns a specific value
        account.sellBond("10",1);
        Assertions.assertEquals(0.0,account.getValueBonds()); // Selling the bond should return 0

    }
}
