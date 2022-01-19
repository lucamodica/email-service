package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.ClientRequest;

import java.io.ObjectOutput;

public class DeleteEmail extends Command {
    private ObjectOutput outputStream;

    public DeleteEmail(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(ClientRequest pkg) {

    }
}
