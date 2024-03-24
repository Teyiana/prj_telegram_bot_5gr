package com.example.commands.settings.set;

import com.example.commands.EditCommand;
import com.example.commands.SettingsCommand;
import com.example.configuration.Currencies;
import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.EMOJI_CHECKED;
import static com.example.commands.settings.CurrenciesCommand.BUTTON_TEXT;

//Команда що виконується на запит "/setCurrencies"
//Встановлює вибрані валюти
//Створює клавіатуру з кнопками вибору валют
public class CurrenciesSetCommand implements EditCommand{

    public static final String COMMAND_NAME = "/setCurrencies";
    private static final String CURRENCY_ARG = "?currency=";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        EditMessageText.EditMessageTextBuilder builder = createEditMethodBuilder(config.getChatId(), messageId);
        if (message.contains(CURRENCY_ARG)) {
            String currencyName = message.substring(message.indexOf(CURRENCY_ARG) + CURRENCY_ARG.length()).trim();
            Currencies currency = Currencies.valueOf(currencyName);
            if (config.getSelectedCurrencies().contains(currency)) {
                config.getSelectedCurrencies().remove(currency);
            } else {
                config.getSelectedCurrencies().add(currency);
            }
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        return builder.build();
    }

    // створюємо кнопку вибору вказаної валюти
    public static List<InlineKeyboardButton> createCurrencyButton(Currencies currency, ChatConfig config) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String text = currency.getEmoji() + currency.getValue();
        if (config.getSelectedCurrencies().contains(currency)) {
            text += " " + EMOJI_CHECKED;
        }

        button.setText(text);
        button.setCallbackData(COMMAND_NAME + CURRENCY_ARG + currency);
        return Collections.singletonList(button);
    }

    //вертає клавіатуру з кнопками вибору валют
    protected List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createCurrencyButton(Currencies.USD, config));
        keyboard.add(createCurrencyButton(Currencies.EUR, config));
        keyboard.add(getNavigationButtons());
        return keyboard;
    }

    @Override
    public List<InlineKeyboardButton> getNavigationButtons() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(createHomeButton());
        buttons.add(createBackButton(SettingsCommand.COMMAND_NAME));
        return buttons;
    }
}
