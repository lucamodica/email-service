package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.model.Model;

public abstract class Controller {

    Model model = ClientApplication.model;

    public String getUserEmail(){
        return model.getClient().getUser().emailAddress();
    }
}
