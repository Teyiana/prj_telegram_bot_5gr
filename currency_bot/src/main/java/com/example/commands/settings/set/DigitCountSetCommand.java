package com.example.commands.settings.set;

import com.example.commands.EditCommand;
import com.example.commands.SettingsCommand;
import com.example.configuration.ChatConfig;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.EMOJI_CHECKED;
import static com.example.commands.settings.DigitCountCommand.BUTTON_TEXT;

//Команда що виконується на запит "/setDigitCount"
//Встановлює вибрану кількість знаків після коми
//Створює клавіатуру з кнопками кількості знаків після коми
public class DigitCountSetCommand implements EditCommand {

    public static final String COMMAND_NAME = "/setDigitCount";
    private static final String NUMBER_ARG = "?number=";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        EditMessageText.EditMessageTextBuilder builder = createEditMethodBuilder(config.getChatId(), messageId);
        if (message.contains(NUMBER_ARG)){
            String number = message.substring(message.indexOf(NUMBER_ARG) + NUMBER_ARG.length()).trim();
            int n = parseNumber(number);
            if (n > 0) {
                config.setDigitCount(n);
            }
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        return builder.build();
    }

    //перетворє строку з числом у число
    private int parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException nfe){
            nfe.printStackTrace();
            return -1;
        }
    }

    //вертає клавіатуру з кнопками вибору кількості знаків після коми
    protected List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createDigitButton(2, config));
        keyboard.add(createDigitButton(3, config));
        keyboard.add(createDigitButton(4, config));
        keyboard.add(getNavigationButtons());
        return keyboard;
    }

    //створює кнопку з вказаним числом знаків після коми
    public static List<InlineKeyboardButton> createDigitButton(int number, ChatConfig config) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        String text = String.valueOf(number);
        if (config.getDigitCount() == number) {
            text += " " + EMOJI_CHECKED;
        }
        button.setText(text);
        button.setCallbackData(COMMAND_NAME + NUMBER_ARG + number);
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
