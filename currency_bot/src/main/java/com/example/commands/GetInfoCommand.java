package com.example.commands;

import com.example.configuration.ChatConfig;
import com.example.configuration.ConfigManager;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.CurrencyBotConstance.EMOJI_GET_INFO;

public class GetInfoCommand implements Command {
    public static final String COMMAND_NAME = "/getInfo";
    public static final String BUTTON_TEXT = "Отримати інфо";
    private static final String INFO_ARG = "?info=";


    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        ChatConfig config = ConfigManager.getChatConfig(chatId);
        if (message.contains(INFO_ARG)) {
            String bank = message.substring(message.indexOf(INFO_ARG) + INFO_ARG.length()).trim();
            if (!Objects.equals(bank, config.getSelectedBank())) {
                config.setSelectedBank(bank);
            }
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(StartCommand.getKeyboard()).build());
        builder.chatId(chatId);
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT + EmojiParser.parseToUnicode(EMOJI_GET_INFO));
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
