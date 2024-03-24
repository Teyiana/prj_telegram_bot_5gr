package com.example.commands;

import com.example.commands.settings.BankCommand;
import com.example.commands.settings.CurrenciesCommand;
import com.example.commands.settings.DigitCountCommand;
import com.example.commands.settings.MessageTimeCommand;
import com.example.commands.settings.set.BankSetCommand;
import com.example.commands.settings.set.CurrenciesSetCommand;
import com.example.commands.settings.set.DigitCountSetCommand;
import com.example.commands.settings.set.MessageTimeSetCommand;

import java.util.HashMap;
import java.util.Map;

//Регістер команд
public class CommandRegistry {
    
    private static CommandRegistry instance;
    private Map<String, Command> registry;

    //Патерн сінглтон, для отримання інстансу CommandRegistry
    public static CommandRegistry getRegistry() {
        if (instance == null) {
            instance = new CommandRegistry();
            instance.init();
        }
        return instance;
    }

    //ініціалізація регістру команд
    private void init() {
        registry = new HashMap<>();
        registry.put(StartCommand.COMMAND_NAME, new StartCommand());
        registry.put(GetInfoCommand.COMMAND_NAME, new GetInfoCommand());
        registry.put(SettingsCommand.COMMAND_NAME, new SettingsCommand());
        registry.put(DigitCountCommand.COMMAND_NAME, new DigitCountCommand());
        registry.put(DigitCountSetCommand.COMMAND_NAME, new DigitCountSetCommand());
        registry.put(BankCommand.COMMAND_NAME, new BankCommand());
        registry.put(BankSetCommand.COMMAND_NAME, new BankSetCommand());
        registry.put(CurrenciesCommand.COMMAND_NAME, new CurrenciesCommand());
        registry.put(CurrenciesSetCommand.COMMAND_NAME, new CurrenciesSetCommand());
        registry.put(MessageTimeCommand.COMMAND_NAME, new MessageTimeCommand());
        registry.put(MessageTimeSetCommand.COMMAND_NAME, new MessageTimeSetCommand());
    }

    //вертає об'єкт команди відповідно повідомлення
    public Command getCommand(String cmd) {
        int queryIndex = cmd.indexOf('?');
        if (queryIndex > 0) {
            cmd = cmd.substring(0, queryIndex);
        }
        return registry.get(cmd);
    }
}
