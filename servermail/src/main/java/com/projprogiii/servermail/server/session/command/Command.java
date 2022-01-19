package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.ServerResponse;

public abstract class Command {
    public abstract ServerResponse handle(ClientRequest req);
}
