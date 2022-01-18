package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponse;
import com.projprogiii.lib.objects.DataPackage;
import com.projprogiii.lib.objects.User;
import com.projprogiii.servermail.ServerApp;

import java.io.IOException;
import java.io.ObjectOutput;

public class FetchEmail extends Command{
    private ObjectOutput outputStream;

    public FetchEmail(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(DataPackage pkg) throws IOException {
        System.out.println("Client " + pkg.getAuth() + " connected, generating db");
        //TODO real fetch handling and sout to log

        if (ServerApp.model.getDbManager().logUser(new User(pkg.getAuth()))){
            sendResponse(ServerResponse.USER_REGISTERED);
        } else {
            sendResponse(ServerResponse.USER_ALREADY_REGISTERED);
        }
    }

    private void sendResponse(ServerResponse response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
