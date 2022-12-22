package edu.investmentportfolio;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cash implements Serializable, Instrument {
    @Serial
    private static final long serialVersionUID = 4L;
    private BigDecimal balance;
    private final BigDecimal interest;

    public Cash() {
        this.balance = BigDecimal.valueOf(0.00);
        this.interest = BigDecimal.valueOf(0.25);
    }

    public void withdraw(BigDecimal cashAmount) {
        if (cashAmount.compareTo(this.balance) < 0) {
            this.balance = setMoney(this.balance);
            cashAmount = setMoney(cashAmount);
            this.balance = this.balance.subtract(cashAmount);
        }
    }

    public void deposit(BigDecimal cashAmount) {
        this.balance = this.balance.add(cashAmount);
        setMoney(this.balance);
    }

    public BigDecimal getBalance() {
        return setMoney(this.balance);
    }

    public void addInterest() {
        this.balance = (this.balance.multiply(this.interest)).add(this.balance);
        setMoney(this.balance);
    }

    @Override
    public String toString() {
        addInterest();
        return "Cash Balance: " + setMoney(this.balance) + ", Interest Rate: " + this.interest + "\n";
    }

    public BigDecimal setMoney(BigDecimal cashBalance) {
        return cashBalance.setScale(2, RoundingMode.HALF_EVEN);
    }
}
