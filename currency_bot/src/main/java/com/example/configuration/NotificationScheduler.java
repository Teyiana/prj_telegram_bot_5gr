package com.example.configuration;

import com.example.CurrencyBotService;
import com.example.commands.GetInfoCommand;
import com.example.commands.SendCommand;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationScheduler implements SendCommand {
    private final ChatConfig chatConfig ;

    private String message;

    private final Timer timer;

    public NotificationScheduler( ChatConfig chatConfig, String message) {

        this.message = message;

        this.chatConfig = chatConfig;
        this.timer = new Timer();

    }



    public void startScheduler( ) {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                LocalTime currentTime = LocalTime.now();


                if( !StringUtils.isNumeric(chatConfig.getSelectedHour().getHour())){
                    stopScheduler();
                    execute(chatConfig.getChatId(),"Сповіщення вимкнене", SendMessage.builder() );
                }else {
                    if (17 == Integer.parseInt(chatConfig.getSelectedHour().getHour())
                            && 0 == 0) {
                        GetInfoCommand getInfoCommand = new GetInfoCommand();

                        try {
                            execute(chatConfig.getChatId(),getInfoCommand.getCurrencyRateText(chatConfig),SendMessage.builder());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }}
            }
        }, 0, 60 * 1000);
    }

    public void execute(String chatId, String message, SendMessage.SendMessageBuilder builder) {

        CurrencyBotService botService = CurrencyBotService.getInstance();

        SendMessage messageNotify = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
        try {
            botService.execute(messageNotify);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void stopScheduler() {
        timer.cancel();
    }

    @Override
    public BotApiMethod<?> execute(ChatConfig config, String message, Integer messageId) {
        return null;
    }


}
