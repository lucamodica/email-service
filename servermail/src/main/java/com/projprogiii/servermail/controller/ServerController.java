package com.projprogiii.servermail.controller;

import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.Model;
import com.projprogiii.servermail.model.log.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.Objects;

public class ServerController {

    @FXML
    private ListView<String> logLst;

    private Model model;
    private LogManager logManager;

    public void initialize(){
        model = model = ServerApp.model;
        logManager = model.getLogManager();

        logLst.itemsProperty().bind(logManager.logProperty());
        logLst.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> p) {

                return new ListCell<>() {
                    @Override
                    protected void updateItem(String t, boolean empty) {
                        super.updateItem(t, empty);
                        setText(Objects.requireNonNullElse(t, ""));
                        getStyleClass().add("single_log");
                    }

                };
            }
        });

    }
}