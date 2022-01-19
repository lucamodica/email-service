package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.ClientRequest;

import java.io.ObjectOutput;

public class SendEmail extends Command{
    private ObjectOutput outputStream;

    public SendEmail(ObjectOutput outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void init(ClientRequest pkg) {

    }
}
