package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.LogManager;
import com.projprogiii.servermail.model.sync.SyncManager;

/**
 * Classes extending Command are initialized server-side using CommandName and args sent by the client.
 * They encapsulate the real command operation to execute, implementing the handle() method.
 */
public abstract class Command {

    protected SyncManager syncManager = ServerApp.model
            .getSyncManager();
    protected LogManager logManager = ServerApp.model
            .getLogManager();

    public abstract ServerResponse handle(ClientRequest req);
    protected abstract void printCommandLog(ClientRequest req, ServerResponseName name);
}
