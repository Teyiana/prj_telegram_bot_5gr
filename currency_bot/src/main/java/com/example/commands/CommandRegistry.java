package com.example.commands;

import com.example.commands.settings.Bank;
import com.example.commands.settings.Currencies;
import com.example.commands.settings.DigitCount;
import com.example.commands.settings.MessageTime;

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
        registry.put(Start.COMMAND_NAME, new Start());
        registry.put(GetInfo.COMMAND_NAME, new GetInfo());
        registry.put(Settings.COMMAND_NAME, new Settings());
        registry.put(DigitCount.COMMAND_NAME, new DigitCount());
        registry.put(Bank.COMMAND_NAME, new Bank());
        registry.put(Currencies.COMMAND_NAME, new Currencies());
        registry.put(MessageTime.COMMAND_NAME, new MessageTime());
    }

    public Command getCommand(String cmd) {
        int queryIndex = cmd.indexOf('?');
        if (queryIndex > 0) {
            cmd = cmd.substring(0, queryIndex);
        }
        return registry.get(cmd);
    }
}
