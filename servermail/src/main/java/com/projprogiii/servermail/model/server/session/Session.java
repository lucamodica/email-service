package com.projprogiii.servermail.model.server.session;

import com.projprogiii.lib.enums.Command;
import com.projprogiii.servermail.ServerApp;

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
            Command command = (Command) inputStream.readObject();
            //commandManager.handleCommand()

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
}
