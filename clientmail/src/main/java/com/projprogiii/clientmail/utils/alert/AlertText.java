package com.projprogiii.clientmail.utils.alert;

public enum AlertText {
    INVALID_RECIPIENTS("Error inserting recipents, please check syntax and make " +
            "sure that the domain is '@unito.it'!"),
    MESSAGE_SENT("Email sent with success!"),
    MESSAGE_DELETED("Email deleted with success!"),
    NO_CONNECTION("Connection not found.");

    public final String text;

    AlertText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
