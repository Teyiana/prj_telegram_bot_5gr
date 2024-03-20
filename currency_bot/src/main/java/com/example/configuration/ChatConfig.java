package com.example.configuration;

import com.example.commands.settings.Currency;
import com.example.commands.settings.Hours;

import java.util.ArrayList;
import java.util.List;

public class ChatConfig {

    private final String chatId;
    private int digitCount;
    private List<Currency> selectedCurrencies = new ArrayList<>();

    private String selectedBank;
    private Hours selectedHour;

    public ChatConfig(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public int getDigitCount() {
        return digitCount;
    }

    public void setDigitCount(int digitCount) {
        this.digitCount = digitCount;
    }

    public List<Currency> getSelectedCurrencies() {
        return selectedCurrencies;
    }

    public void setSelectedCurrencies(List<Currency> selectedCurrencies) {
        this.selectedCurrencies = selectedCurrencies;
    }

    public String getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(String selectedBank) {
        this.selectedBank = selectedBank;
    }

    public Hours getSelectedHour() {
        return selectedHour;
    }

    public void setSelectedHour(Hours selectedHour) {
        this.selectedHour = selectedHour;
    }
}
