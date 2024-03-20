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


public class DigitCountCommand implements Command {
    public static final String COMMAND_NAME = "/digitCount";
    public static final String BUTTON_TEXT = "Кількість знаків після коми";
    private static final String NUMBER_ARG = "?number=";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        ChatConfig config = ConfigManager.getChatConfig(chatId);
        if (message.contains(NUMBER_ARG)){
            String number = message.substring(message.indexOf(NUMBER_ARG) + NUMBER_ARG.length()).trim();
            int n = parseNumber(number);
            if (n > 0) {
                config.setDigitCount(n);
            }
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        builder.chatId(chatId);
    }

    private int parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException nfe){
            nfe.printStackTrace();
            return -1;
        }
    }

    private List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createDigitButton(2, config));
        keyboard.add(createDigitButton(3, config));
        keyboard.add(createDigitButton(4, config));
        return keyboard;
    }

    private List<InlineKeyboardButton> createDigitButton(int number, ChatConfig config) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String text = String.valueOf(number);
        if (config.getDigitCount() == number) {
            text += " " + EmojiParser.parseToUnicode(":white_check_mark:");
        }
        button.setText(text);
        button.setCallbackData(COMMAND_NAME + NUMBER_ARG + number);
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
