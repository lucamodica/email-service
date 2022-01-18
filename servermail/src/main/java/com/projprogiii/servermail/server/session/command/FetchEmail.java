package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;

import java.io.IOException;
import java.io.ObjectOutput;

public class FetchEmail extends Command{
    private final ObjectOutput outputStream;

    public FetchEmail(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(ClientRequest req) throws IOException {
        System.out.println("Client " + req.auth() + " connected");
        //TODO real fetch handling and sout to log

        sendResponse(ServerResponseName.SUCCESS);
    }

    private void sendResponse(ServerResponseName response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
