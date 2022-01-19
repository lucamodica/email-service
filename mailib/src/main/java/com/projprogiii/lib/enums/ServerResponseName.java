package com.projprogiii.lib.enums;

public enum ServerResponseName {
    SUCCESS(null),
    UNKNOWN_COMMAND("Unknown Command"),
    USER_REGISTERED("User registered with success"),
    USER_ALREADY_REGISTERED("User was already registered!"),
    ILLEGAL_PARAMS("Invalid parameters number or type");

    public final String text;

    ServerResponseName(String text) {
        this.text = text;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
