package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.User;
import com.projprogiii.servermail.ServerApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CommandManager {
    ObjectInputStream inputStream = null;
    ObjectOutputStream outputStream = null;

    public CommandManager() {

    }

    public static CommandManager getInstance() {
        return new CommandManager();
    }

    public void handleCommand(CommandName command) throws IOException {
        switch(command){
            case LOGIN:


                break;
        }
    }

}
