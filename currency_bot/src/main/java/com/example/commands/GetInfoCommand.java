package com.example.commands;

import com.example.configuration.ChatConfig;
import com.example.currencyPackage.BankService;
import com.example.currencyPackage.CurrencyRates;
import com.example.ui.PrettyCurrencyService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static com.example.CurrencyBotConstance.EMOJI_GET_INFO;

//Команда що виконується на запит "/getInfo"
public class GetInfoCommand implements SendCommand {

    public static final String COMMAND_NAME = "/getInfo";
    public static final String BUTTON_TEXT = "Отримати інфо";

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        SendMessage.SendMessageBuilder builder = createSendMethodBuilder(config.getChatId());
        builder.text(getCurrencyRateText(config));
        return builder.build();
    }

    public String getCurrencyRateText(ChatConfig config) {
        BankService service = BankService.getInstance();
        List<CurrencyRates> rates = service.getCurrencyRates(Collections.singletonList(config)).get(config);
        StringBuilder sb = new StringBuilder();
        sb.append("Курс в ");
        sb.append(config.getSelectedBank().getNameBank());
        for (CurrencyRates rate: rates) {
            sb.append("\n").append(rate.getCurrency().getValue()).append("\\UAH\n");
            sb.append("Купівля ").append(roundValue(rate.getBuyRate(), config.getDigitCount())).append("\n");
            sb.append("Продаж ").append(roundValue(rate.getSellRate(), config.getDigitCount())).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String roundValue(double rate, int digits){
        BigDecimal r = BigDecimal.valueOf(rate);
        r = r.setScale(digits, RoundingMode.HALF_UP);
        return r.toPlainString();
    }

    @Override
    public List<InlineKeyboardButton> getButtons() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(EMOJI_GET_INFO + BUTTON_TEXT);
        button.setCallbackData(COMMAND_NAME);
        return Collections.singletonList(button);
    }
}
