package com.example.commands.settings;

import com.example.commands.SendCommand;
import com.example.commands.settings.set.BankSetCommand;
import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

//Команда що виконується на запит "/setBank"
//Створює клавіатуру з кнопками вибору банку
public class BankCommand extends BankSetCommand implements SendCommand {

    public static final String COMMAND_NAME = "/bank";
    public static final String BUTTON_TEXT = "Банк";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        SendMessage.SendMessageBuilder builder = createSendMethodBuilder(config.getChatId());
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        return builder.build();
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
