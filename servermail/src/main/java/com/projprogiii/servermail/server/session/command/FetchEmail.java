package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.User;
import com.projprogiii.servermail.ServerApp;

import java.io.IOException;
import java.io.ObjectOutput;

public class FetchEmail extends Command{
    private final ObjectOutput outputStream;

    public FetchEmail(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(ClientRequest pkg) throws IOException {
        System.out.println("Client " + pkg.auth() + " connected, generating db");
        //TODO real fetch handling and sout to log

        if (ServerApp.model.getDbManager().logUser(new User(pkg.auth()))){
            sendResponse(ServerResponseName.USER_REGISTERED);
        } else {
            sendResponse(ServerResponseName.USER_ALREADY_REGISTERED);
        }
    }

    private void sendResponse(ServerResponseName response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
