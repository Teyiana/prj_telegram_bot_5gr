package com.example.configuration;

//Енумерація доступних банків
public enum Banks {
    MONO("Монобанк"), PB("ПриватБанк"), NBU("НБУ");

    private final String nameBank;

    Banks(String nameBank){
        this.nameBank = nameBank;
    }

    public String getNameBank() {
        return nameBank;
    }
}
