package com.projprogiii.lib.enums;

import java.util.Locale;

public enum ServerResponse {
    SUCCESS(null),
    UNKNOWN_COMMAND("Unknown Command");


    public final String text;

    ServerResponse(String text) {
        this.text = text;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
