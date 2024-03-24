package com.example.commands;

import com.example.commands.settings.BankCommand;
import com.example.commands.settings.CurrenciesCommand;
import com.example.commands.settings.DigitCountCommand;
import com.example.commands.settings.MessageTimeCommand;
import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.EMOJI_SETTINGS;

//Команда що виконується на запит "/settings"
public class SettingsCommand implements SendCommand {
    public static final String COMMAND_NAME = "/settings";
    private static final String BUTTON_TEXT = "Налаштування";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        SendMessage.SendMessageBuilder builder = createSendMethodBuilder(config.getChatId());
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard()).build());
        return builder.build();
    }

    //вертає клавіатуру яка має бути відображена після виконання команди Налаштування
    private List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(new BankCommand().getButtons());
        keyboard.add(new CurrenciesCommand().getButtons());
        keyboard.add(new DigitCountCommand().getButtons());
        keyboard.add(new MessageTimeCommand().getButtons());
        keyboard.add(getNavigationButtons());
        return keyboard;
    }

    @Override
    public List<InlineKeyboardButton> getNavigationButtons() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(createHomeButton());
        buttons.add(createBackButton(StartCommand.COMMAND_NAME));
        return buttons;
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(EMOJI_SETTINGS + BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
