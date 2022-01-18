package com.projprogiii.lib.enums;

public enum ServerResponse {
    SUCCESS(null, 0),
    UNKNOWN_COMMAND("Unknown Command", 0);

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
