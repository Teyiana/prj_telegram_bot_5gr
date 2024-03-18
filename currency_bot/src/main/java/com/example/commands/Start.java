package com.example.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Start implements Command {
    public static final String COMMAND_NAME = "/start";

    public static final String GREETINGS_TEXT = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        builder.text(GREETINGS_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard()).build());
        builder.chatId(chatId);
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        return Collections.emptyList();
    }


    private List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(new GetInfo().getButtons());
        keyboard.add(new Settings().getButtons());
        return keyboard;
    }
}
