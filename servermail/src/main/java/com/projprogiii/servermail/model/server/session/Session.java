package com.projprogiii.servermail.model.server.session;

import com.projprogiii.lib.objects.User;
import com.projprogiii.servermail.ServerApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Session implements Runnable{
    Socket currentSocket = null;
    ObjectInputStream inStream = null;
    ObjectOutputStream outStream = null;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerApp.server.getServerPort());

            while (true) {
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
            //now string, needs to be generalized - command/json?
            String emailAddress = (String) inStream.readObject();
            //ServerApp.server.logManager.printSystemLog("Client " + emailAddress + " connected, generating db");
            System.out.println("Client " + emailAddress + " connected, generating db");

            if (ServerApp.model.getDbManager().logUser(new User(emailAddress))){
                outStream.writeObject(true);
            } else {
                outStream.writeObject(false);
            }

            outStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStreams();
        }
    }

    private void openStreams(ServerSocket serverSocket) throws IOException {
        currentSocket = serverSocket.accept();
        inStream = new ObjectInputStream(currentSocket.getInputStream());
        outStream = new ObjectOutputStream(currentSocket.getOutputStream());
        outStream.flush();
    }

    private void closeStreams() {
        try {
            if(inStream != null) { inStream.close(); }
            if(outStream != null) { outStream.close(); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
