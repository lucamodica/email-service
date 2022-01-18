package com.projprogiii.lib.enums;

public enum ServerResponse {
    SUCCESS(null, 0),
    UNKNOWN_COMMAND("Unknown Command", 0),
    USER_REGISTERED("User registered with success", 0),
    USER_ALREADY_REGISTERED("User was already registered!", 0);

    public final String text;
    public final int argsLength;

    ServerResponse(String text, int argsLength) {
        this.text = text;
        this.argsLength = argsLength;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
