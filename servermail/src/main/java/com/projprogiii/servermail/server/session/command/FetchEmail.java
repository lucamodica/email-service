package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FetchEmail implements Command{

    @Override
    public ServerResponse handle(ClientRequest req){
        System.out.println("Fetching...");
        Date date = (Date)req.args().get(0);

        List<Email> list = ServerApp.model.getDbManager().readEmails(req.auth()).stream()
                .filter(email -> email.getDate().compareTo(date) > 0)
                .collect(Collectors.toList());
        return new ServerResponse(ServerResponseName.SUCCESS, list);
    }
}
