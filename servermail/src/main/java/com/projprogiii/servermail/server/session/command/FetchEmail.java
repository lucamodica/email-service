package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FetchEmail extends Command{

    @Override
    public ServerResponse handle(ClientRequest req){
        Date date = (Date) req.arg();
        ServerResponseName name;
        List<Email> emails = null;
        ReentrantReadWriteLock.ReadLock readLock =
                syncManager.getLock(req.auth()).readLock();

        if (date == null){
            name = ServerResponseName.ILLEGAL_PARAMS;
        }
        else {
            readLock.lock();
            emails = ServerApp.model.getDbManager()
                    .retrieveEmails(req.auth()).stream()
                    .filter(email -> email.getDate().compareTo(date) > 0)
                    .toList();
            readLock.unlock();

            name = (emails != null) ?
                    ServerResponseName.SUCCESS :
                    ServerResponseName.OP_ERROR;
        }


        return new ServerResponse(name, emails);
    }
}
