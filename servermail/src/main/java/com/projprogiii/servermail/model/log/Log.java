package com.projprogiii.servermail.model.log;

public record Log(LogType type, String text) {

    @Override
    public String toString() {
        return text;
    }
}
