package com.projprogiii.clientmail.utils.alert;

public enum AlertText {
    INVALID_RECIPIENTS_FORMAT("Error inserting recipents, please check syntax and make sure that the domain is '@unito.it'!"),
    INVALID_RECIPIENTS("Error inserting recipents, some of them may not exists'!"),
    MESSAGE_SENT("Email sent with success!"),
    MESSAGE_DELETED("Email deleted with success!"),
    OP_ERROR("Ops, something went wrong"),
    NEW_EMAILS("New emails!"),
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
