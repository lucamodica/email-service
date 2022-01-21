package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SendEmail extends Command{

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = (Email) req.arg();
        ServerResponseName name;
        ReentrantReadWriteLock.WriteLock writeLock =
                syncManager.getLock(req.auth()).writeLock();


        if (email == null){
            name = ServerResponseName.ILLEGAL_PARAMS;
        }
        else if (!email.getReceivers().stream().allMatch(receiver ->
                    ServerApp.model.getDbManager().checkUser(receiver))){
            name = ServerResponseName.INVALID_RECIPIENTS;
        }
        else {

            writeLock.lock();
            boolean result = ServerApp.model.getDbManager()
                    .saveEmail(email, email.getSender());
            writeLock.unlock();

            if (!result){
                name = ServerResponseName.OP_ERROR;
            }
            else {
                email.setToRead(true);
                boolean allSends = true;

                for (String receiver : email.getReceivers()) {
                    syncManager.addLockEntry(receiver);
                    writeLock = syncManager.getLock(receiver).writeLock();

                    writeLock.lock();
                    if (!ServerApp.model.getDbManager()
                            .saveEmail(email, receiver)) {
                        allSends = false;
                    }
                    writeLock.unlock();

                    syncManager.removeLockEntry(receiver);
                }

                name = (allSends) ?
                        ServerResponseName.SUCCESS :
                        ServerResponseName.OP_ERROR;
            }
        }

        return new ServerResponse(name, null);
    }
}
