package com.projprogiii.clientmail.model.client;

import com.projprogiii.clientmail.model.client.config.ConfigManager;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.lib.utils.CommonUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.System.exit;

public class Client {

    private String user;
    private String serverHost;
    private int serverPort;

    Socket currentSocket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;


    private Client() {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            String emailAddress = configManager.readProperty("user.emailAddress");
            if (CommonUtil.validateEmail(emailAddress)){
                this.user = emailAddress;
            } else {
                throw new IllegalArgumentException();
            }
            serverHost = configManager.readProperty("user.server_host");
            serverPort = Integer.parseInt(configManager.readProperty("user.server_port"));

        } catch (IllegalArgumentException e){
            System.out.println("Illegal emailAddress value! Change it in user.properties file.");
            exit(1);
        }
    }

    public static Client getInstance(){
        return new Client();
    }

    public String getUser(){ return user; }

    //receive response from inputStream, called after sending a command
    private ServerResponse getServerResponse(ClientRequest req) {

        ServerResponse resp;
        try {
            connectToServer();
            outputStream.writeObject(req);
            outputStream.flush();
            resp = (ServerResponse) inputStream.readObject();
        } catch (SocketException ce) {
            resp = null;
        } catch (IOException | ClassNotFoundException se) {
            se.printStackTrace();
            resp = null;
        } finally {
            closeConnections();
        }

        return resp;
    }

    private void connectToServer() throws IOException {
        currentSocket = new Socket(serverHost, serverPort);
        outputStream = new ObjectOutputStream(currentSocket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(currentSocket.getInputStream());
    }

    private void closeConnections() {
        if (currentSocket != null) {
            try {
                inputStream.close();
                outputStream.close();
                currentSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //sent command consist of a ClientRequest object, packing:
    //- the commandName used from server to repack a response
    //- the Object (Date or Email) passed to the server
    public ServerResponse sendCmd(CommandName command, Object arg){
        ClientRequest req = new ClientRequest(user, command, arg);
        return getServerResponse(req);
    }
}
