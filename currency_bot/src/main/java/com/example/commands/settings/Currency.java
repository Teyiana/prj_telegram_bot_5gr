package com.example.commands.settings;

import static com.example.CurrencyBotConstance.EMOJI_EURO;
import static com.example.CurrencyBotConstance.EMOJI_USD;

public enum Currency {
    EUR("EUR", EMOJI_EURO), USD("USD", EMOJI_USD);

    private final String value;
    private final String emoji;

    Currency(String value, String emoji) {
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
