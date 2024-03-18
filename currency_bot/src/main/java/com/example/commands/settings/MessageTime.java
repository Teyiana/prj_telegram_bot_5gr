package com.example.commands.settings;

import com.example.commands.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class MessageTime implements Command {
    public static final String COMMAND_NAME = "/messageTime";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {

    }

//    @Override
//    public List<InlineKeyboardButton> getButtons() {
//        return null;
//    }
}
