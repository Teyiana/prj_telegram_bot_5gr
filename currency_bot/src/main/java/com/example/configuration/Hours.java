package com.example.configuration;

//Енумерація доступних значень годин для надсилання повідомлення
public enum Hours {
    NINE("9"), TEN("10"), ELEVEN("11"), TWELVE("12"),  THIRTEEN("13"), FOURTEEN("14"),
    FIFTEEN("15"), SIXTEEN("16"), SEVENTEEN("17"), EIGHTEEN("18"), NONE("Вимкнути сповіщення")
    ;

    private final String hour;

    Hours(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    // TODO:
    // Для чого цей метод
    public static Hours forValue(String value) {
        for (Hours h : values()) {
            if (h.hour.equals(value)) {
                return h;
            }
        }
        throw new EnumConstantNotPresentException(Hours.class, String.format("Enum value for %s not found!", value));
    }
}
