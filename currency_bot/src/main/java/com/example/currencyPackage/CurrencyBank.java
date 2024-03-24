package com.example.currencyPackage;

public enum CurrencyBank {
    EUR(840),
    USD(978),
    UAH(980);


    private int currencyCode;
    private CurrencyBank(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode=" + currencyCode +
                '}';
    }


}
