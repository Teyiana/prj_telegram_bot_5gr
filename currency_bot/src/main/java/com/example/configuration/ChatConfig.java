package com.example.configuration;

import java.util.ArrayList;
import java.util.List;

//Конфігурація чату з відповідним ідентифікатором
//Містить вибрані валюти, банк, годину сповіщення, та точність округлення
public class ChatConfig {

    // ідентифікатор чату
    private final String chatId;

    //обрана кількість знаків після коми
    private int digitCount = 2;

    //обрані валюти
    private List<Currencies> selectedCurrencies = new ArrayList<>();

    //обраний банк
    private Banks selectedBank = Banks.PB;

    //обрана година для сповіщення
    private Hours selectedHour = Hours.NONE;

    public ChatConfig(String chatId) {
        this.chatId = chatId;
        selectedCurrencies.add(Currencies.USD);
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

    public List<Currencies> getSelectedCurrencies() {
        return selectedCurrencies;
    }

    public void setSelectedCurrencies(List<Currencies> selectedCurrencies) {
        this.selectedCurrencies = selectedCurrencies;
    }

    public Banks getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(Banks selectedBank) {
        this.selectedBank = selectedBank;
    }

    public Hours getSelectedHour() {
        return selectedHour;
    }

    public void setSelectedHour(Hours selectedHour) {
        this.selectedHour = selectedHour;
    }
}
