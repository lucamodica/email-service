package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.model.Model;
import com.projprogiii.clientmail.utils.alert.AlertText;
import javafx.scene.text.TextFlow;

public abstract class Controller {

    Model model = ClientApp.model;

    public String getUserEmail(){
        return model.getClient().getUser();
    }

    public abstract TextFlow getSuccessAlert();
    public abstract TextFlow getDangerAlert();
}
