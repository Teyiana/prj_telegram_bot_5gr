package com.example.dto;

import com.example.currencyPackage.CurrencyBank;

public class CurrencyItemPB {
    private CurrencyBank ccy;
    private CurrencyBank base_ccy;
    private float buy;
    private float sale;

    public CurrencyBank getCcy() {
        return ccy;
    }


    public CurrencyBank getBase_ccy() {
        return base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public float getSale() {
        return sale;
    }
}