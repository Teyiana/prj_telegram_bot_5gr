package com.example.commands.settings;

import com.example.commands.Command;
import com.example.configuration.ChatConfig;
import com.example.configuration.ConfigManager;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class MessageTimeCommand implements Command {
    public static final String COMMAND_NAME = "/messageTime";
    public static final String BUTTON_TEXT = "Час сповіщень";
    private static final String MESSAGE_ARG = "?number=";

    @Override
    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {
        ChatConfig config = ConfigManager.getChatConfig(chatId);
        if (message.contains(MESSAGE_ARG)){
            String hourName = message.substring(message.indexOf(MESSAGE_ARG) + MESSAGE_ARG.length()).trim();
            Hours hour = Hours.valueOf(hourName);
            config.setSelectedHour(hour);
        }
        builder.text(BUTTON_TEXT);
        builder.replyMarkup(InlineKeyboardMarkup.builder().keyboard(getKeyboard(config)).build());
        builder.chatId(chatId);

    }

    private List<List<InlineKeyboardButton>> getKeyboard(ChatConfig config) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(createHourButtons(config, Hours.NINE, Hours.TEN, Hours.ELEVEN));
        keyboard.add(createHourButtons(config, Hours.TWELVE, Hours.THIRTEEN, Hours.FOURTEEN));
        keyboard.add(createHourButtons(config, Hours.FIFTEEN, Hours.SIXTEEN, Hours.SEVENTEEN));
        keyboard.add(createHourButtons(config,  Hours.EIGHTEEN, Hours.NONE));
        return keyboard;
    }

    private List<InlineKeyboardButton> createHourButtons(ChatConfig config, Hours...hours) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Hours hour : hours) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            String text = hour.getHour();
            if (Objects.equals(hour, config.getSelectedHour())) {
                text += " " + EmojiParser.parseToUnicode(":white_check_mark:");
            }
            button.setText(text);
            button.setCallbackData(COMMAND_NAME + MESSAGE_ARG + hour);
            buttons.add(button);
        }
        return buttons;
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
