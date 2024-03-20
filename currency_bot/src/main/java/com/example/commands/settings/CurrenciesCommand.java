package com.example.commands.settings;

import com.example.commands.Command;
import com.example.configuration.ChatConfig;
import com.example.configuration.ConfigManager;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.*;

public class CurrenciesCommand implements Command {
    public static final String COMMAND_NAME = "/currencies";
    public static final String BUTTON_TEXT = "Валюта";
    private static final String CURRENCY_ARG = "?currency=";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        ChatConfig config = ConfigManager.getChatConfig(chatId);
        if (message.contains(CURRENCY_ARG)) {
            String currencyName = message.substring(message.indexOf(CURRENCY_ARG) + CURRENCY_ARG.length()).trim();
            Currency currency = Currency.valueOf(currencyName);
            if (config.getSelectedCurrencies().contains(currency)) {
                config.getSelectedCurrencies().remove(currency);
            } else {
                config.getSelectedCurrencies().add(currency);
            }
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        builder.chatId(chatId);
    }

    private List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createCurrencyButton(Currency.USD, config));
        keyboard.add(createCurrencyButton(Currency.EUR, config));
        return keyboard;
    }

    private List<InlineKeyboardButton> createCurrencyButton(Currency currency, ChatConfig config) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String text = currency.getEmoji() + currency.getValue();
        if (config.getSelectedCurrencies().contains(currency)) {
            text += " " + EmojiParser.parseToUnicode(EMOJI_CHECKED);
        }

        button.setText(text);
        button.setCallbackData(COMMAND_NAME + CURRENCY_ARG + currency);
        return Collections.singletonList(button);
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
