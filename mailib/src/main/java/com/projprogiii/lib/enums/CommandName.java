package com.projprogiii.lib.enums;

import java.io.Serializable;

public enum CommandName implements Serializable {
    FETCH_EMAIL,
    SEND_EMAIL,
    MARK_AS_READ,
    DELETE_EMAIL;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
