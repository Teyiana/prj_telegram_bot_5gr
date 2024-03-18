package com.example.commands;

import com.example.commands.settings.Bank;
import com.example.commands.settings.Currencies;
import com.example.commands.settings.DigitCount;
import com.example.commands.settings.MessageTime;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Settings implements Command {
    public static final String COMMAND_NAME = "/settings";
    private static final String BUTTON_TEXT = "Налаштування";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard()).build());
        builder.chatId(chatId);
    }

    private List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(new DigitCount().getButtons());
        keyboard.add(new Bank().getButtons());
        keyboard.add(new Currencies().getButtons());
        keyboard.add(new MessageTime().getButtons());
        return keyboard;
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
