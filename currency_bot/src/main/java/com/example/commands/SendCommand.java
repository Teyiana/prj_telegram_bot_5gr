package com.example.commands;

import com.example.configuration.ChatConfig;
import com.example.configuration.ConfigManager;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendCommand extends Command{

    //створює SendMessageBuilder для виконання поточної команди
    default SendMessage.SendMessageBuilder createSendMethodBuilder(String chatId) {
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        builder.chatId(chatId);
        return builder;
    }


}
