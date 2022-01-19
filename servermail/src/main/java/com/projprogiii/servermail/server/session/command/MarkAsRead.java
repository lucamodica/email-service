package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.ClientRequest;

import java.io.ObjectOutput;

public class MarkAsRead extends Command {
    private ObjectOutput outputStream;

    public MarkAsRead(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(ClientRequest pkg) {

    }
}
