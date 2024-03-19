package com.example.configuration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static ConfigManager instance;
    private Map<String, ChatConfig> configCache;

    public static ChatConfig getChatConfig(String chatId) {
        return getInstance().getOrCreateConfig(chatId);
    }

    private ChatConfig getOrCreateConfig(String chatId) {
        return configCache.computeIfAbsent(chatId, k -> new ChatConfig(chatId));
    }

    private static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.init();
        }
        return instance;
    }

    private void init() {
        configCache = new HashMap<>();
        //TODO: load from json
    }
}
