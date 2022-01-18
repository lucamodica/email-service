package com.projprogiii.servermail.model.server.command;

import com.projprogiii.lib.enums.Command;
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

    public void handleCommand(Command command) throws IOException {
        switch(command){
            case LOGIN:

                System.out.println("Client " + " connected, generating db");

                if (ServerApp.model.getDbManager().logUser(new User(emailAddress))){
                    outputStream.writeObject(true);
                } else {
                    outputStream.writeObject(false);
                }
                //ServerApp.server.logManager.printSystemLog("Client " + emailAddress + " connected, generating db");

                outputStream.flush();
                break;
        }
    }

}
