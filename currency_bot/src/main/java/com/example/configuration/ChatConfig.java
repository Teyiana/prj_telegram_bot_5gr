package com.example.configuration;

import java.util.ArrayList;
import java.util.List;

public class ChatConfig {

    private final String chatId;
    private int digitCount;
    private List<String> selectedCurrencies = new ArrayList<>();

    private String selectedBank;

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

    public List<String> getSelectedCurrencies() {
        return selectedCurrencies;
    }

    public void setSelectedCurrencies(List<String> selectedCurrencies) {
        this.selectedCurrencies = selectedCurrencies;
    }

    public String getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(String selectedBank) {
        this.selectedBank = selectedBank;
    }
}
