package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

public class MarkAsRead extends Command {

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = (Email) req.args().get(0);
        if (email == null){ return new ServerResponse(ServerResponseName.ILLEGAL_PARAMS, null); }
        ServerApp.model.getDbManager().markAsReadEmail(email, req.auth());
        return new ServerResponse(ServerResponseName.SUCCESS, null);
    }
}
