package com.projprogiii.lib.enums;

import java.io.Serializable;

public enum CommandName implements Serializable {
    FETCH_EMAIL(1),
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
