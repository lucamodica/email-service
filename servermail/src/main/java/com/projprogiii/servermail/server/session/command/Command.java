package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.ServerResponse;

public interface Command {
    ServerResponse handle(ClientRequest req);
}
