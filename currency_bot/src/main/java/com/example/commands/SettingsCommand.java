package com.example.commands;

import com.example.commands.settings.BankCommand;
import com.example.commands.settings.CurrenciesCommand;
import com.example.commands.settings.DigitCountCommand;
import com.example.commands.settings.MessageTimeCommand;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.EMOJI_SETTINGS;

public class SettingsCommand implements Command {
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
        keyboard.add(new DigitCountCommand().getButtons());
        keyboard.add(new BankCommand().getButtons());
        keyboard.add(new CurrenciesCommand().getButtons());
        keyboard.add(new MessageTimeCommand().getButtons());
        return keyboard;
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT + EmojiParser.parseToUnicode(EMOJI_SETTINGS));
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
