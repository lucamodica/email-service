package com.projprogiii.servermail.model.server.session;

import com.projprogiii.servermail.ServerApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.projprogiii.lib.utils.CommonUtil.validateEmail;
import static com.projprogiii.servermail.ServerApp.server;

public class Session implements Runnable{
    Socket currentSocket = null;
    ObjectInputStream inStream = null;
    ObjectOutputStream outStream = null;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerApp.server.getServerPort());

            while (true) {
                serveClient(serverSocket);
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

    private void serveClient(ServerSocket serverSocket) {
        try {
            openStreams(serverSocket);

            String emailAdress = (String) inStream.readObject();

            if (validateEmail(emailAdress)){
                outStream.writeObject("Correct email address");
            } else {
                outStream.writeObject("Error in email address format");
            }

            outStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStreams();
        }
    }

    // apre gli stream necessari alla connessione corrente
    private void openStreams(ServerSocket serverSocket) throws IOException {
        currentSocket = serverSocket.accept();
        System.out.println("Server Connesso");

        inStream = new ObjectInputStream(currentSocket.getInputStream());
        outStream = new ObjectOutputStream(currentSocket.getOutputStream());
        outStream.flush();
    }

    // Chiude gli stream utilizzati durante l'ultima connessione
    private void closeStreams() {
        try {
            if(inStream != null) {
                inStream.close();
            }

            if(outStream != null) {
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
