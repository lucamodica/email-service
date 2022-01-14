package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.model.Model;

public abstract class Controller {

    Model model = ClientApp.model;

    public String getUserEmail(){
        return model.getClient().getUser().emailAddress();
    }
}
