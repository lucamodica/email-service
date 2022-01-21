package com.projprogiii.lib.enums;

public enum ServerResponseName {
    SUCCESS(null),
    ILLEGAL_PARAMS("Invalid parameters number or type."),
    INVALID_RECIPIENTS("Invalid recipients adresses."),
    OP_ERROR("Something went wrong.");

    public final String text;

    ServerResponseName(String text) {
        this.text = text;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
