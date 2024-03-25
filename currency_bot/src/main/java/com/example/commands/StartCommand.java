package com.example.commands;

import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Команда що виконується на запит "/start"
public class StartCommand implements SendCommand {

    // TODO: Потрібно, щоб була якась кнопка одразу, по типу натиснусти на старт
    public static final String COMMAND_NAME = "/start";
    public static final String GREETINGS_TEXT = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
    private static final String BUTTON_TEXT = "Почати";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        SendMessage.SendMessageBuilder builder = createSendMethodBuilder(config.getChatId());
        builder.text(GREETINGS_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard()).build());
        return builder.build();
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }

    //вертає клавіатуру яка має бути відображена після виконання команди старт
    public static List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(new GetInfoCommand().getButtons());
        keyboard.add(new SettingsCommand().getButtons());
        return keyboard;
    }
}
