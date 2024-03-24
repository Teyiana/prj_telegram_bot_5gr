package com.example.configuration;

import static com.example.CurrencyBotConstance.EMOJI_EURO;
import static com.example.CurrencyBotConstance.EMOJI_USD;

//Енумерація доступних значень валюк надсилання повідомлення
public enum Currencies {
    EUR("EUR", EMOJI_EURO), USD("USD", EMOJI_USD);

    private final String value;
    private final String emoji;

    Currencies(String value, String emoji) {
        this.value = value;
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getValue() {
        return value;
    }
}
