package com.projprogiii.servermail.server.session;

import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.db.DbManager;
import com.projprogiii.servermail.server.session.command.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Session implements Runnable{
    Socket currentSocket = null;
    ObjectInputStream inputStream = null;
    ObjectOutputStream outputStream = null;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerApp.server.getServerPort());

            while (true) {
                //always listening
                clientDbInit(serverSocket);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (currentSocket!=null) {
                try {
                    currentSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clientDbInit(ServerSocket serverSocket) {
        try {
            openStreams(serverSocket);

            //TODO change command read to json
            ClientRequest req = (ClientRequest) inputStream.readObject();
            Command command = createCommand(req.cmdName());

            DbManager db = ServerApp.model.getDbManager();
            if (db.checkUser(req.auth())){
                System.out.println("User exists");
            }
            else {
                System.out.println("Creating db for the new user " + req.auth());
                db.addUser(req.auth());
            }
            command.init(req);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStreams();
        }
    }

    private void openStreams(ServerSocket serverSocket) throws IOException {
        currentSocket = serverSocket.accept();
        inputStream = new ObjectInputStream(currentSocket.getInputStream());
        outputStream = new ObjectOutputStream(currentSocket.getOutputStream());
        outputStream.flush();
    }

    private void closeStreams() {
        try {
            if(inputStream != null) { inputStream.close(); }
            if(outputStream != null) { outputStream.close(); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Command createCommand(CommandName cmdname){
        switch(cmdname){
            case FETCH_EMAIL -> {
                return new FetchEmail(outputStream);
            }
            case SEND_EMAIL -> {
                return new SendEmail();
            }
            case MARK_AS_READ -> {
                return new MarkAsRead();
            }
            case DELETE_EMAIL -> {
                return new DeleteEmail();
            }
            default -> {
                return null;
            }
        }
    }
}
