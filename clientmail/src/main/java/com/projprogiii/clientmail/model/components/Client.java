package com.projprogiii.clientmail.model.components;

import com.projprogiii.lib.objects.User;

public class Client {

    private User user;
    private final ConfigManager configManager;

    public Client() {
        this.configManager = null;
    }

    public Client(String emailAddress) {
        this.configManager = null;
        this.user = new User(emailAddress);
    }

    public User getUser(){ return user; }
}
