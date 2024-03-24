package com.example.configuration;

import java.util.HashMap;
import java.util.Map;

//Менеджер конфігурацій чатів
public class ConfigManager {

    private static ConfigManager instance;
    private Map<String, ChatConfig> configCache;

    //Патерн сінглтон, для отримання інстансу ConfigManager
    private static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.init();
        }
        return instance;
    }

    //Статичний метод що повертає конфігурацію чату для заданого ідентифікатора
    public static ChatConfig getChatConfig(String chatId) {
        return getInstance().getOrCreateConfig(chatId);
    }

    //Вертає існуючу або створює дефолтну конфігурацію чату
    private ChatConfig getOrCreateConfig(String chatId) {
        return configCache.computeIfAbsent(chatId, k -> new ChatConfig(chatId));
    }

    //Ініціалізація менедера конфігурацій - завантаження данних збережених раніше при першому запуску програми
    private void init() {
        configCache = new HashMap<>();
        //TODO: load from json
    }
}
