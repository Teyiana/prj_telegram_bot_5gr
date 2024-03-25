package com.example.currencyPackage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.dto.CurrencyItemPB;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class PrivateBankCurrencyService  implements CurrencyService {
    @Override
    public double getRateBuy(CurrencyBank currency, int prize) {

        String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
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
            throw new IllegalStateException("Can't connect to PrivatAPI");
        }

        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemPB.class)
                .getType();

        List<CurrencyItemPB> currencyItems = new Gson().fromJson(json, typeToken);

        // Find UAH/USD
        double result = 0.0;
        if (prize == 2) {
            result = currencyItems.stream()
                    .filter(it -> it.getCcy() == currency)
                    .filter(it -> it.getBase_ccy() == CurrencyBank.UAH)
                    .map(CurrencyItemPB::getBuy)
                    .findFirst()
                    .orElseThrow();
        } else {
            result = currencyItems.stream()
                    .filter(it -> it.getCcy() == currency)
                    .filter(it -> it.getBase_ccy() == CurrencyBank.UAH)
                    .map(CurrencyItemPB::getSale)
                    .findFirst()
                    .orElseThrow();
        }
        return result;

    }
}
