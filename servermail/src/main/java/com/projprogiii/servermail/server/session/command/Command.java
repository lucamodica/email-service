package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;

import java.io.IOException;

public abstract class Command {
    public abstract void init(ClientRequest pkg) throws IOException;

}
