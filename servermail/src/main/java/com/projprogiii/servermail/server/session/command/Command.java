package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.sync.SyncManager;

public abstract class Command {

    protected SyncManager syncManager = ServerApp.model
            .getSyncManager();

    public abstract ServerResponse handle(ClientRequest req);
}
