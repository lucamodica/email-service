package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;
import javafx.application.Platform;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SendEmail extends Command{

    @Override
    public ServerResponse handle(ClientRequest req) {
        Email email = req.arg();
        ServerResponseName name;
        ReentrantReadWriteLock.WriteLock writeLock =
                syncManager.getLock(req.auth()).writeLock();


        if (email == null){
            name = ServerResponseName.ILLEGAL_PARAMS;
            Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): illegal params passed!")
            );
        }
        else if (!email.getReceivers().stream().allMatch(receiver ->
                    ServerApp.model.getDbManager().checkUser(receiver))){
            name = ServerResponseName.INVALID_RECIPIENTS;
            Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): some of the receivers does " +
                            "not exists in the database!")
            );
        }
        else {

            writeLock.lock();
            boolean result = ServerApp.model.getDbManager()
                    .saveEmail(email, email.getSender());
            writeLock.unlock();

            if (!result){
                name = ServerResponseName.OP_ERROR;
                Platform.runLater(() -> logManager.printError(
                        "ERROR (" + req.cmdName().toString() + " for "
                                + req.auth() + "): operation for sender " +
                                "failed!")
                );
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

                if (allSends) {
                    name = ServerResponseName.SUCCESS;
                    Platform.runLater(() -> logManager.printLog(
                            "Email for " + req.auth() +
                                    "successfully sent!")
                    );
                }
                else {
                    name = ServerResponseName.OP_ERROR;
                    Platform.runLater(() -> logManager.printError(
                            "ERROR (" + req.cmdName().toString() + " for "
                                    + req.auth() + "): operation for a receiver " +
                                    "failed!")
                    );
                }
            }
        }

        return new ServerResponse(name, null);
    }
}
