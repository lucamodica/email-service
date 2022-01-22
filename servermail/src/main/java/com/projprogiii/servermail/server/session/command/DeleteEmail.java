package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.LogType;
import javafx.application.Platform;

import java.io.FileNotFoundException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DeleteEmail extends Command {

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
        else {
            writeLock.lock();
            try {
                if (ServerApp.model.getDbManager()
                        .deleteEmail(email, req.auth())) {
                    name = ServerResponseName.SUCCESS;
                    Platform.runLater(() -> logManager.printLog(
                            "Email (" + req.arg().getId() + ".txt) for " +
                                    req.auth() + "successfully deleted!", LogType.NORMAL)
                    );
                }
                else {
                    name = ServerResponseName.OP_ERROR;
                    Platform.runLater(() -> logManager.printError(
                            "ERROR (" + req.cmdName().toString() + " for "
                                    + req.auth() + "): operation failed!")
                    );
                }
            } catch (FileNotFoundException e) {
                name = ServerResponseName.SUCCESS;
                Platform.runLater(() -> logManager.printLog(
                        "Email (" + req.arg().getId() + ".txt) for " +
                                req.auth() + "successfully deleted!", LogType.NORMAL)
                );
            }
            writeLock.unlock();
        }

        return new ServerResponse(name, null);
    }
}
