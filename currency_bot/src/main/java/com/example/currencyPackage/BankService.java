package com.example.currencyPackage;

import com.example.configuration.Banks;
import com.example.configuration.ChatConfig;
import com.example.configuration.Currencies;

import java.util.*;

public class BankService {

    private static final int SELL = 1;
    private static final int BUY = 2;
    private static final BankService instance = new BankService();

    private final MonoCurrencyService monoService = new MonoCurrencyService();
    private final PrivateBankCurrencyService privateBankService = new PrivateBankCurrencyService();
    private final NBUCurrencyService nbuService = new NBUCurrencyService();

    private BankService(){}

    public static BankService getInstance() {
        return instance;
    }

    public Map<ChatConfig, List<CurrencyRates>> getCurrencyRates(List<ChatConfig> configs) {
        Map<Banks, Set<Currencies>> currencyRequestMap = createCurrencyRequestMap(configs);
        Map<Banks, Map<Currencies, CurrencyRates>> currencyRatesMap = getCurrencyRates(currencyRequestMap);
        return prepareChatResults(currencyRatesMap, configs);
    }

    private Map<ChatConfig, List<CurrencyRates>> prepareChatResults(Map<Banks, Map<Currencies, CurrencyRates>> currencyRatesMap,
                                                                    List<ChatConfig> configs) {
        Map<ChatConfig, List<CurrencyRates>> chatRatesResult = new HashMap<>();
        for (ChatConfig config : configs) {
            Map<Currencies, CurrencyRates> bankRates = currencyRatesMap.get(config.getSelectedBank());
            List<CurrencyRates> currencyRates = new ArrayList<>();
            for (Currencies currency : config.getSelectedCurrencies()) {
                CurrencyRates rates = bankRates.get(currency);
                currencyRates.add(rates.clone());
            }
            chatRatesResult.put(config, currencyRates);
        }
        return chatRatesResult;
    }

    private Map<Banks, Map<Currencies, CurrencyRates>> getCurrencyRates(Map<Banks, Set<Currencies>> currencyRequestMap) {
        Map<Banks, Map<Currencies, CurrencyRates>> currencyRatesMap = new HashMap<>();
        for (Map.Entry<Banks, Set<Currencies>> entry : currencyRequestMap.entrySet()) {
            Banks bank = entry.getKey();
            CurrencyService currencyService = getCurrencyService(bank);
            Map<Currencies, CurrencyRates> currencyRates = getCurrencyRates(bank, currencyService, entry.getValue());
            currencyRatesMap.put(bank, currencyRates);
        }
        return currencyRatesMap;
    }

    private Map<Currencies, CurrencyRates> getCurrencyRates(Banks bank, CurrencyService currencyService, Set<Currencies> currencySet) {
        Map<Currencies, CurrencyRates> currencyRatesSet = new HashMap<>();
        for (Currencies currency : currencySet) {
            CurrencyBank currencyBank = getCurrencyBank(currency);
            CurrencyRates rates = new CurrencyRates();
            rates.setBank(bank);
            rates.setCurrency(currency);
            rates.setSellRate(currencyService.getRateBuy(currencyBank, SELL));
            rates.setBuyRate(currencyService.getRateBuy(currencyBank, BUY));
            currencyRatesSet.put(currency, rates);
        }
        return currencyRatesSet;
    }

    private Map<Banks, Set<Currencies>> createCurrencyRequestMap(List<ChatConfig> configs) {
        Map<Banks, Set<Currencies>> currencyRequestMap = new HashMap<>();
        for (ChatConfig config : configs) {
            Set<Currencies> currencySet = currencyRequestMap
                    .computeIfAbsent(config.getSelectedBank(), k -> new HashSet<>());
            currencySet.addAll(config.getSelectedCurrencies());
        }
        return currencyRequestMap;
    }

    private CurrencyBank getCurrencyBank(Currencies currency) {
        return switch (currency) {
            case EUR -> CurrencyBank.EUR;
            case USD -> CurrencyBank.USD;
        };
    }

    private CurrencyService getCurrencyService(Banks selectedBank) {
        return switch (selectedBank) {
            case MONO -> monoService;
            case PB -> privateBankService;
            case NBU -> nbuService;
            default -> throw new IllegalArgumentException("Unsupported Bank requested");
        };
    }
}
