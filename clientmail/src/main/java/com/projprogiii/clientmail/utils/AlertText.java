package com.projprogiii.clientmail.utils;

public enum AlertText {
    INVALID_RECIPIENTS("Error inserting recipents, please check syntax and make " +
            "sure that the domain is '@unito.it'!");

    public final String text;

    AlertText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
