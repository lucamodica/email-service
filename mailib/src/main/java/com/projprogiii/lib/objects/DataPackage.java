package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.CommandName;

import java.io.Serializable;

public class DataPackage implements Serializable {
    private String auth;
    private CommandName cmd;
    private Email email;

    public DataPackage(String auth, CommandName cmd, Email email) {
        this.auth = auth;
        this.cmd = cmd;
        this.email = email;
    }

    public String getAuth() {
        return auth;
    }

    public CommandName getCommandName() {
        return cmd;
    }

    public Email getEmail() {
        //effettuare controlli su email=null oppure sul cmd nei casi che non prevedono una mail come arg
        return email;
    }
}
