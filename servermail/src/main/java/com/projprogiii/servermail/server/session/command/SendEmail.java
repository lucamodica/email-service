package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.LogType;
import javafx.application.Platform;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SendEmail extends Command{

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = req.arg();
        ServerResponseName name;
        ReentrantReadWriteLock.WriteLock writeLock =
                syncManager.getLock(req.auth()).writeLock();

        //check for null object or if the recipients of the email actually exist.
        if (email == null){
            name = ServerResponseName.ILLEGAL_PARAMS;
            printCommandLog(req, name);
        }
        else if (!email.getReceivers().stream().allMatch(receiver ->
                    ServerApp.model.getDbManager().checkUser(receiver))){
            name = ServerResponseName.INVALID_RECIPIENTS;
            printCommandLog(req, name);
        }
        else {

            writeLock.lock();
            boolean result = ServerApp.model.getDbManager()
                    .saveEmail(email, email.getSender());
            writeLock.unlock();

            if (!result){
                name = ServerResponseName.OP_ERROR;
                printCommandLog(req, name);
            }
            else {
                email.setToRead(true);
                boolean allSends = true;

                for (String receiver : email.getReceivers()) {
                    if (!Objects.equals(receiver, req.auth())){
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
                }

                if (allSends) {
                    name = ServerResponseName.SUCCESS;
                    printCommandLog(req, name);
                }
                else {
                    name = ServerResponseName.OP_ERROR;
                    printCommandLog(req, name);
                }
            }
        }

        return new ServerResponse(name, null);
    }

    protected void printCommandLog(ClientRequest req, ServerResponseName name){
        switch(name){
            case ILLEGAL_PARAMS ->  Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): illegal params passed!"));
            case INVALID_RECIPIENTS -> Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): some of the receivers does " +
                            "not exists in the database!"));
            case SUCCESS -> Platform.runLater(() -> logManager.printLog(
                    "Email for " + req.auth() +
                            " successfully sent!", LogType.NORMAL));
            case OP_ERROR -> Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): operation for sender " +
                            "failed!"));
        }
    }
}
