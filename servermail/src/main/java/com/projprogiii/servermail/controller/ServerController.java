package com.projprogiii.servermail.controller;

import com.projprogiii.servermail.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerController {

    @FXML
    private ListView<String> logLst;

    private Model model;

    public void initialize(){

        model = Model.getInstance();
        logLst.itemsProperty().bind(model.logProperty());
    }

}