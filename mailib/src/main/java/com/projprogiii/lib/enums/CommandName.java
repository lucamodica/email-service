package com.projprogiii.lib.enums;

public enum CommandName {
    FETCH_EMAIL(0),
    //CHECK_EMAIL(0),
    SEND_EMAIL(1),
    MARK_AS_READ(1),
    DELETE_EMAIL(1);

    public final int argsLength;

    CommandName(int argsLength) {
        this.argsLength = argsLength;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
