package com.example.commands.settings.set;

import com.example.commands.EditCommand;
import com.example.commands.SettingsCommand;
import com.example.commands.settings.BankCommand;
import com.example.configuration.Banks;
import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.CurrencyBotConstance.EMOJI_CHECKED;

//Команда що виконується на запит "/setBank"
//Встановлює вибраний банк
//Створює клавіатуру з кнопками вибору банку
public class BankSetCommand implements EditCommand {

    public static final String COMMAND_NAME = "/setBank";
    private static final String BANK_ARG = "?bank=";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        EditMessageText.EditMessageTextBuilder builder = createEditMethodBuilder(config.getChatId(), messageId);
        if (message.contains(BANK_ARG)) {
            String bankName = message.substring(message.indexOf(BANK_ARG) + BANK_ARG.length()).trim();
            Banks bank = Banks.valueOf(bankName);
            config.setSelectedBank(bank);
        }
        builder.text(BankCommand.BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        return builder.build();
    }

    //вертає клавіатуру з кнопками вибору банку
    protected List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createBankButton(Banks.NBU, config));
        keyboard.add(createBankButton(Banks.PB, config));
        keyboard.add(createBankButton(Banks.MONO, config));
        keyboard.add(getNavigationButtons());
        return keyboard;
    }

    // створюємо кнопку вибору вказаного банку
    public static List<InlineKeyboardButton> createBankButton(Banks bank, ChatConfig config) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String text = bank.getNameBank();
        if (Objects.equals(bank, config.getSelectedBank())) {
            text += " " + EMOJI_CHECKED;
        }
        button.setText(text);
        button.setCallbackData(COMMAND_NAME + BANK_ARG + bank);
        return Collections.singletonList(button);
    }

    @Override
    public List<InlineKeyboardButton> getNavigationButtons() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(createHomeButton());
        buttons.add(createBackButton(SettingsCommand.COMMAND_NAME));
        return buttons;
    }

}
