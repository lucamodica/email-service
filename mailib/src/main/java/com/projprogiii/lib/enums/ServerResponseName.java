package com.projprogiii.lib.enums;

public enum ServerResponseName {
    SUCCESS(null),
    ILLEGAL_PARAMS("Invalid parameters."),
    INVALID_RECIPIENTS("Invalid recipients address, some of them may not exists."),
    OP_ERROR("Ops, something went wrong.");

    public final String text;

    ServerResponseName(String text) {
        this.text = text;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
