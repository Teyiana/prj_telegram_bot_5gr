package com.example;

import com.example.commands.Command;
import com.example.commands.CommandRegistry;
import com.example.configuration.ConfigManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.CurrencyBotConstance.BOT_NAME;
import static com.example.CurrencyBotConstance.BOT_TOKEN;

public class CurrencyBotService extends TelegramLongPollingBot {

    private static final String COMMAND_NOT_FOUND = "Вибачте, команду не знайдено!\nСпробуйте знову";
    private final CommandRegistry commandRegistry;


    private static  CurrencyBotService instance;
    public CurrencyBotService() {
        super(BOT_TOKEN);
        commandRegistry = CommandRegistry.getRegistry();
        instance = this;
    }

    public static CurrencyBotService getInstance() {
        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processMessage(update.getMessage().getChatId().toString(), update.getMessage().getText(), null);
        } else if (update.hasCallbackQuery()) {
            MaybeInaccessibleMessage message = update.getCallbackQuery().getMessage();
            processMessage(message.getChatId().toString(), update.getCallbackQuery().getData(), message.getMessageId());
        }
    }

    private void processMessage(String chatId, String message, Integer callbackMessageId) {
        Command command = commandRegistry.getCommand(message);
        BotApiMethod<?> method;
        if (command != null) {
            method = command.execute(ConfigManager.getChatConfig(chatId), message, callbackMessageId);
        } else {
            method = SendMessage.builder().chatId(chatId).text(COMMAND_NOT_FOUND).build();
        }
        try {
            execute(method);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
        }
}
