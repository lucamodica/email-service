package com.projprogiii.servermail.server.session;

import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.server.session.command.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
                sessionOperationHandler(serverSocket);
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

    private void sessionOperationHandler(ServerSocket serverSocket) {
        try {
            ServerResponse response;
            //open stream and get client request
            currentSocket = serverSocket.accept();

            outputStream = new ObjectOutputStream(currentSocket.getOutputStream());
            inputStream = new ObjectInputStream(currentSocket.getInputStream());

            ClientRequest req = (ClientRequest) inputStream.readObject();
            System.out.println(req);

            //OP block
            if(req.cmdName().argsLength != req.args().size()){
                response = new ServerResponse(ServerResponseName.ILLEGAL_PARAMS, null);
            } else {
                Command command = createCommand(req.cmdName());
                checkAuth(req.auth());
                response = command.handle(req);
            }

            //writing server response
            outputStream.flush();
            outputStream.writeObject(response);
            outputStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStreams();
        }
    }

    private void closeStreams() {
        try {
            if(inputStream != null) { inputStream.close(); }
            if(outputStream != null) { outputStream.close(); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Command createCommand(CommandName cmdName){
        switch(cmdName){
            case FETCH_EMAIL -> {
                return new FetchEmail();
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
            default -> throw new IllegalStateException("Unexpected value: " + cmdName);
        }
    }

    private void checkAuth(String auth){
        if (ServerApp.model.getDbManager().addUser(auth)){
            System.out.println("User " + auth + " registered");
        }
    }
}
