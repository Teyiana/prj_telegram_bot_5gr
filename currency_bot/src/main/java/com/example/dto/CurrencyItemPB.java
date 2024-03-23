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

    public void setCcy(CurrencyBank ccy) {
        this.ccy = ccy;
    }

    public CurrencyBank getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(CurrencyBank base_ccy) {
        this.base_ccy = base_ccy;
    }

    public float getBuy() {
        return buy;
    }

    public void setBuy(float buy) {
        this.buy = buy;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }


}