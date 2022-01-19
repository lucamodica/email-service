package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;

import java.io.IOException;
import java.io.ObjectOutput;

public class InvalidCommand extends Command{
    private final ObjectOutput outputStream;

    public InvalidCommand(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(ClientRequest pkg) throws IOException {
        sendResponse(ServerResponseName.UNKNOWN_COMMAND);
    }

    private void sendResponse(ServerResponseName response) throws IOException {
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
