package com.example;

import com.example.commands.Command;
import com.example.commands.CommandRegistry;
import com.example.commands.settings.Currency;
import com.example.configuration.ChatConfig;
import com.example.configuration.ConfigManager;
import com.example.currencyPackage.*;
import com.example.ui.PrettyCurrencyService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.CurrencyBotConstance.EMOJI_USD;
import java.util.LinkedList;
import java.util.List;

import static com.example.CurrencyBotConstance.*;

public class CurrencyBotService extends TelegramLongPollingBot {

    private static final String COMMAND_NOT_FOUND = "Вибачте, команду не знайдено!\nСпробуйте знову";
    private CommandRegistry commandRegistry;


    private static int bank_priz = 0;
    private static int sign_priz = 0;
    private static int cash_priz = 0;
    private static int time_priz = 0;
    private static int bankId;
    private  static int signId;
    private static String cashId;
    private  static int timeId;

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
        List<Currency> cashs = new LinkedList<>();
        ChatConfig config = ConfigManager.getChatConfig(chatId);

        Command command = commandRegistry.getCommand(message);
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        if (command != null) {
            command.execute(chatId, message, builder);
        } else {
            builder.text(COMMAND_NOT_FOUND);
        }

        if (message.equals("/start")) {

            bankId = 1;       // значення за замовчуванням
            signId = 2;       // значення за замовчуванням
            cashId = "USD";   // значення за замовчуванням
            timeId = 0;       // значення за замовчуванням

        } else if (message.equals("/bank")) {
            bank_priz = 1;
        } else if (message.equals("/currencies")) {
            cash_priz = 1;
        } else if (message.equals("/digitCount")) {
             sign_priz = 1;
        } else if (message.equals("/messageTime")) {
            time_priz = 1;
        }

        if (message.equals("/getInfo")) {

            if (bank_priz != 0) {
                //------------------------- банк
                String bank = config.getSelectedBank();
                if (bank.equals("НБУ")) {
                    bankId = 2;
                } else if (bank.equals("ПриватБанк")) {
                    bankId = 1;
                } else {
                    bankId = 3;
                }
            }

            if (sign_priz != 0) {
                //------------------------- кількість знаків після коми
                signId = config.getDigitCount();
            }

            if (cash_priz != 0) {
                //------------------------- валюта
                cashs = config.getSelectedCurrencies();
            }

            if (time_priz != 0) {
                //------------------------- відсрочення
                timeId = Integer.valueOf(config.getSelectedHour().getHour());
            }



        if (cash_priz == 0) {
            read(chatId);
        } else {
            for (int j = 0; j < cashs.size(); j++) {
                cashId = cashs.get(j).getValue();

                read(chatId);
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

    private  void read(String chatId) {
        if (bankId == 1) {
            currencyService = new PrivateBankCurrencyService();
        } else if (bankId == 2) {
            currencyService = new NBUCurrencyService();
        } else {
            currencyService = new MonoCurrencyService();
        }

        prettyCurrencyService = new PrettyCurrencyService();
        CurrencyBank currency = CurrencyBank.valueOf(cashId);

        for (int i = 0; i <= 2; i++) {
            double currencyRateBuy = 0.0;

            if (i != 0) {
                currencyRateBuy = currencyService.getRateBuy(currency, i);
                //  System.out.println(currencyRateBuy);
            }
            String prettyText = prettyCurrencyService.convert(currencyRateBuy, currency, i, bankId, signId);

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
}
