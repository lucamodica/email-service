package com.projprogiii.lib.enums;

public enum Command {
    FETCH_EMAIL(0),
    CHECK_EMAIL(0),
    SEND_EMAIL(1),
    READ_EMAIL(1),
    DELETE_EMAIL(1);

    public final int length;

    Command(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
