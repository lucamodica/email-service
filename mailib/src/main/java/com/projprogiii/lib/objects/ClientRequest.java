package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.CommandName;

import java.io.Serializable;

public record ClientRequest(String auth, CommandName cmdName,
                            Object arg) implements Serializable {

    @Override
    public String toString() {
        return "ClientRequest{" +
                "auth='" + auth + '\'' +
                ", cmdName=" + cmdName +
                ", args=" + arg +
                '}';
    }
}
