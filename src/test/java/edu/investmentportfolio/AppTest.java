package edu.investmentportfolio;

/**
 * Unit test for simple App.
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AppTest {
    BigDecimal Zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);

    @Test
    public void test_buyStock() throws IOException, InterruptedException {
        Stock stock = new Stock();
        Assertions.assertNotEquals(Zero,stock.buyStock("AAPL")); //It should be Appls's current stock price which could vary. At the time this test was created it was 135
        Assertions.assertEquals(Zero,stock.buyStock("gibberish")); //Should return 0 because it is an invalid stock name
    }
    @Test
    public void test_getValueStocks() throws IOException, InterruptedException {

        Account account = new Account("Test", "Case", BigDecimal.valueOf(2000));
        account.addStock("MSFT",BigDecimal.ZERO);
        Assertions.assertEquals(Zero,account.getValueStocks()); // Buying 0 stocks should return 0

        account.addStock("MSFT",BigDecimal.valueOf(2));
        Assertions.assertNotEquals(Zero,account.getValueStocks()); // Buying 2 stocks should return a number other than 0

        account.sellStock("MSFT",BigDecimal.valueOf(2));
        Assertions.assertEquals(Zero,account.getValueStocks()); // Selling 2 stocks should return 0
    }

    @Test
    public void test_buyCrypto() throws IOException, InterruptedException {
        Crypto crypto = new Crypto();
        Assertions.assertNotEquals(Zero,crypto.buyCrypto("bitcoin")); //It should be return bitcoin's current price (16882.55 when test was first performed)
        Assertions.assertEquals(Zero,crypto.buyCrypto("gibberish")); //Should return 0 because it is an invalid crypto name
    }
    @Test
    public void test_getValueCrypto() throws IOException, InterruptedException {
        Account account = new Account("Test", "Case", BigDecimal.valueOf(100000));
        account.addCrypto("ethereum",BigDecimal.ZERO);
        Assertions.assertEquals(Zero,account.getValueCrypto()); // Buying 0 crypto should return 0

        account.addCrypto("ethereum",BigDecimal.valueOf(2));
        Assertions.assertNotEquals(Zero,account.getValueCrypto()); // Buying 2 crypto should return a number other than 0

        account.sellCrypto("ethereum", BigDecimal.valueOf(2));
        Assertions.assertEquals(Zero,account.getValueCrypto()); // Selling 2 crypto should return 0
    }
    @Test
    public void test_getValueBonds() throws IOException, InterruptedException {
        Account account = new Account("Test", "Case", BigDecimal.valueOf(100000));
        account.addBond(10,BigDecimal.valueOf(1000),BigDecimal.ZERO);
        Assertions.assertEquals(Zero,account.getValueBonds()); // Buying 0 bonds should return 0

        account.addBond(10,BigDecimal.valueOf(1000),BigDecimal.valueOf(1));
        Assertions.assertEquals(BigDecimal.valueOf(1029.35),account.getValueBonds()); // Value of bonds is based on a constant equation and returns a specific value

        account.sellBond(10,BigDecimal.ONE);
        Assertions.assertEquals(Zero,account.getValueBonds()); // Selling the bond should return 0

    }

    @Test
    public void test_addCash() throws IOException, InterruptedException {
        Account account = new Account("Test", "Case", BigDecimal.valueOf(5));

        account.addCrypto("bitcoin",BigDecimal.valueOf(1));
        Assertions.assertEquals(Zero,account.getValueCrypto());

        account.addCash(BigDecimal.valueOf(20000));
        account.addCrypto("bitcoin",BigDecimal.valueOf(1));
        Assertions.assertNotEquals(Zero,account.getValueCrypto());
    }
}
