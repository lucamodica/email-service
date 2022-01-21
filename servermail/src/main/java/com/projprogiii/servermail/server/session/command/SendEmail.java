package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

public class SendEmail implements Command{

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = (Email) req.arg();

        ServerResponseName name;

        if (email == null){
            name = ServerResponseName.ILLEGAL_PARAMS;
        }
        else if (!email.getReceivers().stream().allMatch(receiver ->
                    ServerApp.model.getDbManager().checkUser(receiver))){
            name = ServerResponseName.INVALID_RECIPIENTS;
        }
        else {

            //sender write lock
            boolean result = ServerApp.model.getDbManager()
                    .storeEmail(email, email.getSender());
            //sender unlock

            if (!result){
                name = ServerResponseName.ILLEGAL_PARAMS;
            }
            else {
                email.setToRead(true);
                boolean allSends = true;

                for (String receiver : email.getReceivers()) {
                    //current receiver write lock
                    if (!ServerApp.model.getDbManager()
                            .storeEmail(email, receiver)) {
                        allSends = false;
                    }
                    //current receiver unlock
                }

                name = (allSends) ?
                        ServerResponseName.SUCCESS :
                        ServerResponseName.ILLEGAL_PARAMS;
            }
        }

        return new ServerResponse(name, null);
    }
}
