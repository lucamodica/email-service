package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

import java.util.List;

public class FetchEmail extends Command{

    @Override
    public ServerResponse handle(ClientRequest req){
        System.out.println("Fetching...");
        List<Email> list = ServerApp.model.getDbManager().readEmails(req.auth());
        return new ServerResponse(ServerResponseName.SUCCESS, list);
    }
}
