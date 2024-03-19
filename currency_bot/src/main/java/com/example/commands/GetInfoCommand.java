package com.example.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

public class GetInfoCommand implements Command {
    public static final String COMMAND_NAME = "/getInfo";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {

    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        return Collections.emptyList();
    }
}
