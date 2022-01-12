package com.projprogiii.clientmail.model.components;

import com.projprogiii.clientmail.model.components.configmanager.ConfigManager;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.User;
import com.projprogiii.lib.utilities.Util;

import static java.lang.System.exit;

public class Client {

    private User user;
    private String serverHost;
    private String serverPort;

    private Client() {
        ConfigManager configManager = ConfigManager.getInstance();

        try{

            String emailAddress = configManager.readProperty("user.emailAddress");
            if (Util.validateEmail(emailAddress)){
                this.user = new User(emailAddress);
            }
            else {
                throw new IllegalArgumentException();
            }
            serverHost = configManager.readProperty("user.server_host");
            serverPort = configManager.readProperty("user.server_port");

        } catch (IllegalArgumentException e){
            System.out.println("Illegal emailAddress value! Change it in user.properties file.");
            exit(1);
        }
    }
    public static Client getInstance(){
        return new Client();
    }


    public User getUser(){ return user; }

    public void sendEmail(Email email){
        //TODO implement sending email communication to server
    }

    public void deleteEmail(Email email){
        //TODO send command to server in order to delete specific email from db
    }
}
