package com.projprogiii.clientmail.model.client;

import com.projprogiii.clientmail.model.client.config.ConfigManager;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.DataPackage;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.User;
import com.projprogiii.lib.utils.CommonUtil;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class Client {

    private User user;
    private String serverHost;
    private int serverPort;
    private Date lastFetch;

    Socket currentSocket = null;
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    private Client() {
        ConfigManager configManager = ConfigManager.getInstance();

        try{

            String emailAddress = configManager.readProperty("user.emailAddress");
            if (CommonUtil.validateEmail(emailAddress)){
                this.user = new User(emailAddress);
            }
            else {
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

    public User getUser(){ return user; }

    //TODO generizzare per comandi
    public void login(){
        ExecutorService exec = Executors.newFixedThreadPool(3);

        exec.execute(()-> communicationTest());

        //useful for waiting before termination of server
        try {
            exec.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void communicationTest(){
        boolean success = false;

        while(!success) {
            success = communicationTestAux();

            if(success) {
                continue;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO generizzare per comandi
    private boolean communicationTestAux() {
        try {
            connectToServer();
            Thread.sleep(100);

            DataPackage pkg = new DataPackage(user.emailAddress(), CommandName.FETCH_EMAIL, new Email(user.emailAddress(), Collections.singletonList("lucamodica@unito.it"), "test obj", "test text"));

            outputStream.writeObject(pkg);
            outputStream.flush();
            //receive response
            boolean b = (boolean) inputStream.readObject();
            System.out.println("[Client " + user.emailAddress() + "] Logged => " + b);

            return true;
        } catch (ConnectException ce) {
            // nothing to be done
            return false;
        } catch (IOException se) {
            se.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
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

    public void sendCmd(CommandName command, String... args){

        JSONObject jsonObject = new JSONObject()
            .put("auth", user.emailAddress())
            .put("command", command)
            .put("args", args);

        String s = jsonObject.toString();
        System.out.println(jsonObject);
        System.out.println(s);

        JSONObject jsonObject1 = new JSONObject(s);
        System.out.println(jsonObject1.getString("command"));
    }
}
