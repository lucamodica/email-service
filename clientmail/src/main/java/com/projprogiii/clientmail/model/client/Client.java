package com.projprogiii.clientmail.model.client;

import com.projprogiii.clientmail.controller.Controller;
import com.projprogiii.clientmail.model.client.config.ConfigManager;
import com.projprogiii.clientmail.utils.responsehandler.ResponseHandler;
import com.projprogiii.clientmail.utils.responsehandler.SuccessHandler;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.ClientRequest;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.lib.utils.CommonUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {

    private static final int threadsNumber = 3;
    private String user;
    private String serverHost;
    private int serverPort;
    private int fetchPeriod;

    private ExecutorService operationPool;
    private Socket currentSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

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
            fetchPeriod = Integer.parseInt(configManager.readProperty("user.fetch_period_s"));

            operationPool = Executors.newFixedThreadPool(threadsNumber);

        } catch (IllegalArgumentException e){
            System.out.println("Illegal emailAddress value! Change it in user.properties file.");
            System.exit(1);
        }
    }

    public static Client getInstance(){
        return new Client();
    }
    public String getUser(){ return user; }

    public int getFetchPeriod() {
        return fetchPeriod;
    }

    /**
     * receive response from inputStream, called after sending a command
     */
    private ServerResponse getServerResponse(ClientRequest req) {

        ServerResponse resp = null;
        try {
            connectToServer();
            outputStream.writeObject(req);
            outputStream.flush();
            resp = (ServerResponse) inputStream.readObject();
        } catch (IOException ignored) {
            //When user suddenly close the connection
            //and cannot write on the socket stream
        } catch (ClassNotFoundException e){
            e.printStackTrace();
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
                outputStream.close();
                inputStream.close();
                currentSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sent command consist of a ClientRequest object, packing:
     * the commandName used from server to repack a response,
     * the Object (Date or Email) passed to the server
     */
    public void sendCmd(CommandName command, Email arg,
                                  Controller controller,
                                  SuccessHandler successHandler,
                                  Object successArg){

        ClientRequest req = new ClientRequest(user, command, arg);
        operationPool.execute(() -> {
            ServerResponse resp = getServerResponse(req);
            ResponseHandler.handleResponse(resp, controller,
                    successHandler, successArg);
        });
    }

    public void shutdown(){
        operationPool.shutdown();
        try {
            System.out.println(operationPool.awaitTermination((2L *
                    threadsNumber) + 1, TimeUnit.SECONDS) ?
                    "" : "Timeout elapsed before operationPool thread pool termination.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
