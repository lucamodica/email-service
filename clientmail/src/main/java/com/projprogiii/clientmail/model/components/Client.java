package com.projprogiii.clientmail.model.components;

import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.User;

public class Client {

    private User user;
    private final ConfigManager configManager;

    private Client() {
        this.configManager = null;
    }
    private Client(String emailAddress) {
        this.configManager = null;
        this.user = new User(emailAddress);
    }

    public static Client getInstance(String emailAddress){
        return new Client(emailAddress);
    }

    public User getUser(){ return user; }

    public void deleteEmailClient(Email email){
        //TODO send command to server in order to delete specific email from db
    }
}
