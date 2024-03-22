package com.example.dto;

import com.example.currencyPackage.CurrencyBank;

public class CurrencyItemNBU {
    private CurrencyBank cc;
    private float rate;

    public CurrencyBank getCc() {
        return cc;
    }

    public void setCc(CurrencyBank cc) {
        this.cc = cc;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
