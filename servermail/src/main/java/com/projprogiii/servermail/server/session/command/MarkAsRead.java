package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

public class MarkAsRead implements Command {

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = (Email) req.arg();

        //write lock
        ServerResponseName name = (ServerApp.model.getDbManager()
                .markAsReadEmail(email, req.auth())) ?
                ServerResponseName.SUCCESS :
                ServerResponseName.ILLEGAL_PARAMS;
        //unlock

        return new ServerResponse(name, null);
    }
}
