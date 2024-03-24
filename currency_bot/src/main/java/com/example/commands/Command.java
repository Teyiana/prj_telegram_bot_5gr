package com.example.commands;

import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.BUTTON_TITLE_BACK;
import static com.example.CurrencyBotConstance.BUTTON_TITLE_HOME;

public interface Command {

    //виконати команду, має повертати метод повідомлення
    BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId);

    //повертає кнопки що відповідають даній команді
    default List<InlineKeyboardButton> getButtons() {
        return Collections.emptyList();
    }

    //повертає навігаційні кнопки (додому і назад) що відповідають даній команді
    default List<InlineKeyboardButton> getNavigationButtons() {
        return Collections.emptyList();
    }

    //створює кнопку додому для клавіатури поточної команди
    default InlineKeyboardButton createHomeButton() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TITLE_HOME);
        button.setCallbackData(StartCommand.COMMAND_NAME);
        return button;
    }

    //створює кнопку назад для клавіатури поточної команди
    default InlineKeyboardButton createBackButton(String commandName) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TITLE_BACK);
        button.setCallbackData(commandName);
        return button;
    }
}
