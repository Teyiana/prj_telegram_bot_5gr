package com.example.commands;

import com.example.commands.settings.BankCommand;
import com.example.commands.settings.CurrenciesCommand;
import com.example.commands.settings.DigitCountCommand;
import com.example.commands.settings.MessageTimeCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    
    private static CommandRegistry instance;
    private Map<String, Command> registry;

    public static CommandRegistry getRegistry() {
        if (instance == null) {
            instance = new CommandRegistry();
            instance.init();
        }
        return instance;
    }

    private void init() {
        registry = new HashMap<>();
        registry.put(StartCommand.COMMAND_NAME, new StartCommand());
        registry.put(GetInfoCommand.COMMAND_NAME, new GetInfoCommand());
        registry.put(SettingsCommand.COMMAND_NAME, new SettingsCommand());
        registry.put(DigitCountCommand.COMMAND_NAME, new DigitCountCommand());
        registry.put(BankCommand.COMMAND_NAME, new BankCommand());
        registry.put(CurrenciesCommand.COMMAND_NAME, new CurrenciesCommand());
        registry.put(MessageTimeCommand.COMMAND_NAME, new MessageTimeCommand());
    }

    public Command getCommand(String cmd) {
        int queryIndex = cmd.indexOf('?');
        if (queryIndex > 0) {
            cmd = cmd.substring(0, queryIndex);
        }
        return registry.get(cmd);
    }
}
