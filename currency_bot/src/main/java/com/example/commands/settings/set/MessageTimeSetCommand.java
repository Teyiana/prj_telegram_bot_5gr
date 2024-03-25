package com.example.commands.settings.set;

import com.example.commands.EditCommand;
import com.example.commands.SettingsCommand;
import com.example.configuration.Hours;
import com.example.configuration.ChatConfig;
import com.example.configuration.NotificationScheduler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.CurrencyBotConstance.EMOJI_CHECKED;
import static com.example.commands.settings.MessageTimeCommand.BUTTON_TEXT;

//Команда що виконується на запит "/setMessageTime"
//Встановлює вказаний вибраний час
//Створює клавіатуру з кнопками годин
public class MessageTimeSetCommand implements EditCommand {

    public static final String COMMAND_NAME = "/setMessageTime";
    private static final String MESSAGE_ARG = "?number=";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        EditMessageText.EditMessageTextBuilder builder = createEditMethodBuilder(config.getChatId(), messageId);
        if (message.contains(MESSAGE_ARG)){
            String hourName = message.substring(message.indexOf(MESSAGE_ARG) + MESSAGE_ARG.length()).trim();
            Hours hour = Hours.valueOf(hourName);
            config.setSelectedHour(hour);
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        NotificationScheduler notificationScheduler = new NotificationScheduler(config,message);
        notificationScheduler.startScheduler();
        return builder.build();
    }

    //вертає клавіатуру з кнопками вибору години сповіщення
    protected List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createHourButtons(config, Hours.NINE, Hours.TEN, Hours.ELEVEN));
        keyboard.add(createHourButtons(config, Hours.TWELVE, Hours.THIRTEEN, Hours.FOURTEEN));
        keyboard.add(createHourButtons(config, Hours.FIFTEEN, Hours.SIXTEEN, Hours.SEVENTEEN));
        keyboard.add(createHourButtons(config,  Hours.EIGHTEEN, Hours.NONE));
        keyboard.add(getNavigationButtons());
        return keyboard;
    }

    //створює рядок кнопок вибору години сповіщення для вказаних годин
    public static List<InlineKeyboardButton> createHourButtons(ChatConfig config, Hours... hours) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Hours hour : hours) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            String text = hour.getHour();
            if (Objects.equals(hour, config.getSelectedHour())) {
                text += " " + EMOJI_CHECKED;
            }
            button.setText(text);
            button.setCallbackData(COMMAND_NAME + MESSAGE_ARG + hour);
            buttons.add(button);
        }
        return buttons;
    }

    @Override
    public List<InlineKeyboardButton> getNavigationButtons() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(createHomeButton());
        buttons.add(createBackButton(SettingsCommand.COMMAND_NAME));
        return buttons;
    }

}
