package com.example.currencyPackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.dto.CurrencyItemMB;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MonoCurrencyService implements CurrencyService {
    // Змінив priz на prize
    @Override
    public double getRateBuy(CurrencyBank currency, int prize) {
        String url = "https://api.monobank.ua/bank/currency";
        String json;

        // get JSON
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to MonoAPI");
        }

        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemMB.class)
                .getType();

        List<CurrencyItemMB> currencyItems = new Gson().fromJson(json, typeToken);

        // Find UAH/USD    Currency.valueOf(code)
        double result = 0.0;

        if (prize == 2) {
            result = currencyItems.stream()
                    .filter(it -> it.getCurrencyCodeA() == currency.getCurrencyCode())
                    .map(CurrencyItemMB::getRateBuy)
                    .findFirst()
                    .orElseThrow();


        } else {
            result = currencyItems.stream()
                    .filter(it -> it.getCurrencyCodeA() == currency.getCurrencyCode())
                    .map(CurrencyItemMB::getRateSell)
                    .findFirst()
                    .orElseThrow();
        }


        return result;
    }
}


