package com.example.commands;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface EditCommand extends Command{

    //створює EditMessageTextBuilder для виконання поточної команди
    default EditMessageText.EditMessageTextBuilder createEditMethodBuilder(String chatId, Integer messageId){
        EditMessageText.EditMessageTextBuilder builder = EditMessageText.builder();
        builder.chatId(chatId);
        builder.messageId(messageId);
        return builder;
    }
}
