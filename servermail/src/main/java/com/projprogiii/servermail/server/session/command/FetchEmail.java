package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

import java.util.Date;
import java.util.List;

public class FetchEmail implements Command{

    @Override
    public ServerResponse handle(ClientRequest req){
        Date date = (Date) req.arg();

        //read lock
        List<Email> emails = ServerApp.model.getDbManager()
                .retrieveEmails(req.auth()).stream()
                .filter(email -> email.getDate().compareTo(date) > 0)
                .toList();
        System.out.println(emails);
        //unlock

        ServerResponseName name = (emails == null) ?
                ServerResponseName.ILLEGAL_PARAMS :
                ServerResponseName.SUCCESS;

        return new ServerResponse(name, emails);
    }
}
