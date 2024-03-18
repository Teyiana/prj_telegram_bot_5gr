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
import java.util.Objects;

public class Bank implements Command {
    public static final String COMMAND_NAME = "/bank";
    public static final String BUTTON_TEXT = "Банк";
    private static final String BANK_ARG = "?bank=";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        ChatConfig config = ConfigManager.getChatConfig(chatId);
        if (message.contains(BANK_ARG)) {
            String bank = message.substring(message.indexOf(BANK_ARG) + BANK_ARG.length()).trim();
            if (!Objects.equals(bank, config.getSelectedBank())) {
                config.setSelectedBank(bank);
            }
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        builder.chatId(chatId);

    }

    private List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createBankButton("НБУ", config));
        keyboard.add(createBankButton("ПриватБанк", config));
        keyboard.add(createBankButton("Монобанк", config));
        return keyboard;
    }

    private List<InlineKeyboardButton> createBankButton(String bank, ChatConfig config) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String text = bank;
        if (Objects.equals(bank, config.getSelectedBank())) {
            text += " " + EmojiParser.parseToUnicode(":white_check_mark:");
        }
        button.setText(text);
        button.setCallbackData(COMMAND_NAME + BANK_ARG + bank);
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
