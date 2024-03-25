package com.example.ui;

import com.example.currencyPackage.CurrencyBank;

import java.math.BigDecimal;

public class PrettyCurrencyService {


    // TODO: можливо потрібно зробити в цьому класі Enum
    // prize = 0 - 1 строка message
    // prize = 1 - 3 строка message (продаж)
    // prize = 2 - 2 строка message (купівля)


    private BigDecimal roundedRate;
    // TODO: можливо краще зробити блок ініціалізації
    private String template = "";
    private String bankName = "";


    // Тут я замінив не priz, а prize
    private void createRoundRate(double rate,int signId) {
        roundedRate = new BigDecimal(rate);
        roundedRate = roundedRate.setScale(signId, BigDecimal.ROUND_DOWN);
    }

    public  String convert(double rate, CurrencyBank currency, int prize, int bankId, int signId) {

        if (prize == 0) {
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

        } else if (prize == 2) {
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


