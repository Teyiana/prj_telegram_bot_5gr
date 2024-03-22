package com.example;

import com.example.commands.Command;
import com.example.commands.CommandRegistry;
import com.example.currencyPackage.*;
import com.example.ui.PrettyCurrencyService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static com.example.CurrencyBotConstance.BOT_NAME;
import static com.example.CurrencyBotConstance.BOT_TOKEN;

public class CurrencyBotService extends TelegramLongPollingBot {

    private static final String COMMAND_NOT_FOUND = "Вибачте, команду не знайдено!\nСпробуйте знову";
    private CommandRegistry commandRegistry;

    private static int bankId;
    private  static int prettyId;
    private static String cash;
    private CurrencyService currencyService;
    private PrettyCurrencyService prettyCurrencyService;

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

        SendMessage responseMessage = new SendMessage();

        if (message.equals("/start")) {
            bankId = 1;   // значення за замовчуванням
            prettyId = 3;  // значення за замовчуванням
            cash = "USD";   // значення за замовчуванням

        }

        Command command = commandRegistry.getCommand(message);
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        if (command != null) {
            command.execute(chatId, message, builder);
        } else {
            builder.text(COMMAND_NOT_FOUND);
        }

        if (message.equals("/getInfo")) {

            if (bankId == 1) {
                currencyService = new PrivateBankCurrencyService();
            } else if (bankId == 2) {
                currencyService = new NBUCurrencyService();
            } else {
                currencyService = new MonoCurrencyService();
            }

            prettyCurrencyService = new PrettyCurrencyService();

            String callbackQuery = update.getCallbackQuery().getData();
            CurrencyBank currency = CurrencyBank.valueOf(cash);

            for (int i = 0; i <= 2; i++) {
                double currencyRateBuy = 0.0;

                if (i != 0) {
                    currencyRateBuy = currencyService.getRateBuy(currency, i);
                  //  System.out.println(currencyRateBuy);
                }
                String prettyText = prettyCurrencyService.convert(currencyRateBuy, currency, i, bankId, prettyId);

                SendMessage resMessage = new SendMessage();
                resMessage.setText(prettyText);
                resMessage.setChatId(chatId);

                try {
                    execute(resMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();

                }

            }
        }

        if (message.equals("/getInfo")) {
            //
        } else {
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
