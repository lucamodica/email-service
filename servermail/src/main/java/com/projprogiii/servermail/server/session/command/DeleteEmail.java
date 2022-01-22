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
            printCommandLog(req, name);
        }
        else {
            writeLock.lock();
            try {
                if (ServerApp.model.getDbManager()
                        .deleteEmail(email, req.auth())) {
                    name = ServerResponseName.SUCCESS;
                    printCommandLog(req, name);
                }
                else {
                    name = ServerResponseName.OP_ERROR;
                    printCommandLog(req, name);
                }
            } catch (FileNotFoundException e) {
                //email was already deleted from the db but not yet synced to client
                name = ServerResponseName.SUCCESS;
                printCommandLog(req, name);
            }
            writeLock.unlock();
        }

        return new ServerResponse(name, null);
    }

    protected void printCommandLog(ClientRequest req, ServerResponseName name){
        switch(name){
            case ILLEGAL_PARAMS -> Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): illegal params passed!"));
            case SUCCESS -> Platform.runLater(() -> logManager.printLog(
                    "Email (" + req.arg().getId() + ".txt) for " +
                            req.auth() + " successfully deleted!", LogType.NORMAL));
            case OP_ERROR -> Platform.runLater(() -> logManager.printError(
                    "ERROR (" + req.cmdName().toString() + " for "
                            + req.auth() + "): operation failed!"));
        }
    }
}
