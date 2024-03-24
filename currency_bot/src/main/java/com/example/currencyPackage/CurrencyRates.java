package com.example.currencyPackage;

import com.example.configuration.Banks;
import com.example.configuration.Currencies;

public class CurrencyRates implements Cloneable {

    private Banks bank;
    private Currencies currency;
    private double sellRate;
    private double buyRate;

    public Banks getBank() {
        return bank;
    }

    public void setBank(Banks bank) {
        this.bank = bank;
    }

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    @Override
    public CurrencyRates clone() {
        try {
            return (CurrencyRates) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
