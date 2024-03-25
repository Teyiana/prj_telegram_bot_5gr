package com.example.ui;

import com.example.currencyPackage.CurrencyBank;

import java.math.BigDecimal;

public class PrettyCurrencyService {

    // можливо потрібно зробити в цьому класі Enum
    // TODO:
    // priz = 0 - 1 строка message
    // priz = 1 - 3 строка message (продаж)
    // priz = 2 - 2 строка message (купівля)


    private BigDecimal roundedRate;
    private String template = "";
    private String bankName = "";


    private void createRoundRate(double rate,int signId) {
        roundedRate = new BigDecimal(rate);
        roundedRate = roundedRate.setScale(signId, BigDecimal.ROUND_DOWN);
    }

    public  String convert(double rate, CurrencyBank currency, int priz, int bankId, int signId) {

        if (priz == 0) {
            bankName = "";

            if (bankId == 1) {
                bankName = "у Приватбанку";
            } else if (bankId == 2) {
                bankName = "у НБУ";
            } else {
                bankName = "у Монобанку";
            }

            template = "Курс " + bankName + " ${currency} / UAH";
            return template
                    .replace("${currency}", currency.name());

        } else if (priz == 2) {
            template = "Купівля: ${rate}";
            createRoundRate(rate, signId);
            return template
                    .replace("${rate}", roundedRate + "");

        } else {
            template = "Продаж ${rate}";
            createRoundRate(rate, signId);
            return template
                    .replace("${rate}", roundedRate + "");

        }
    }
}


