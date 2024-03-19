package com.example.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

public interface Command {
    void execute(String chatId, String message, SendMessage.SendMessageBuilder builder);

    default List<InlineKeyboardButton> getButtons() {
        return Collections.emptyList();
    }
}
