package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.CommandName;

import java.io.Serializable;

public record ClientRequest(String auth, CommandName cmdName,
                            Email arg) implements Serializable {

    @Override
    public String toString() {
        return "user: " + arg + ", operation: " + cmdName.toString()
                + ", arg = " + arg.getId() + " email.";
    }
}
