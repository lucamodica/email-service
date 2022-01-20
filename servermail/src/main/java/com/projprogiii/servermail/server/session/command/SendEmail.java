package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

public class SendEmail implements Command{

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = (Email) req.args().get(0);
        ServerResponseName name;
        if (email == null){
            name = ServerResponseName.ILLEGAL_PARAMS;
        }
        else if (email.getReceivers().stream().allMatch(receiver ->
                    ServerApp.model.getDbManager().checkUser(receiver))){
            name = ServerResponseName.INVALID_RECIPIENTS;
        }
        else {
            ServerApp.model.getDbManager().saveEmail(email);
            name = ServerResponseName.SUCCESS;
        }
        return new ServerResponse(name, null);
    }
}
