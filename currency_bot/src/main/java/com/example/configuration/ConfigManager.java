package com.example.configuration;

import com.example.currencyPackage.CurrencyBank;
import com.example.currencyPackage.CurrencyService;
import com.example.currencyPackage.PrivateBankCurrencyService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static ConfigManager instance;
    private Map<String, ChatConfig> configCache;

    public static ChatConfig getChatConfig(String chatId) {
        return getInstance().getOrCreateConfig(chatId);
    }

    private ChatConfig getOrCreateConfig(String chatId) {
        return configCache.computeIfAbsent(chatId, k -> new ChatConfig(chatId));
    }

    private static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.init();
        }
        return instance;
    }

    private void init() {
        configCache = new HashMap<>();
        //TODO: load from json
    }


    private void vvv() {
       // String message = "/getInfo";

     //   if (message.equals("/getInfo")) {

            // if (bankId == 1) {
            CurrencyService currencyService = new PrivateBankCurrencyService();
            //   } else if (bankId == 2) {
            //   CurrencyService currencyService = new NBUCurrencyService();
            //   } else {
            //    CurrencyService currencyService = new MonoCurrencyService();
            //  }
            String cash = "USD";
            int prettyId = 3;

            CurrencyBank currency = CurrencyBank.valueOf(cash);
            for (int i = 1; i <= 2; i++) {
                double currencyRate = currencyService.getRateBuy(currency, i);
                BigDecimal roundedRate = new BigDecimal(currencyRate);
                roundedRate = roundedRate.setScale(prettyId, BigDecimal.ROUND_DOWN);

                System.out.println("USD= " + currencyRate + "=" + roundedRate);
            }
      //  }
    }

}
