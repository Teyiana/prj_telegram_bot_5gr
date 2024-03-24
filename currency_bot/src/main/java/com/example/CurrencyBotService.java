package com.example;

import com.example.commands.Command;
import com.example.commands.CommandRegistry;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.CurrencyBotConstance.BOT_NAME;
import static com.example.CurrencyBotConstance.BOT_TOKEN;

public class CurrencyBotService extends TelegramLongPollingBot {

    private static final String COMMAND_NOT_FOUND = "Вибачте, команду не знайдено!\nСпробуйте знову";
    private CommandRegistry commandRegistry;
    public CurrencyBotService() {
        super(BOT_TOKEN);
        commandRegistry = CommandRegistry.getRegistry();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processMessage(update, update.getMessage().getChatId().toString(), update.getMessage().getText());
        } else if (update.hasCallbackQuery()) {
            processMessage(update, update.getCallbackQuery().getMessage().getChatId().toString(), update.getCallbackQuery().getData());
        }
    }

    private void processMessage(Update update, String chatId, String message) {
        Command command = commandRegistry.getCommand(message);
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        if (command != null) {
            command.execute(chatId, message, builder);
        } else {
            builder.text(COMMAND_NOT_FOUND);
        }
        try {
            execute(builder.build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
