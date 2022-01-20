package com.projprogiii.servermail.controller;

import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.LogManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ServerController {

    @FXML
    private ListView<String> logLst;

    public void initialize(){
        LogManager logManager = ServerApp.model.getLogManager();

        logLst.itemsProperty().bind(logManager.logProperty());
        logLst.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(String logItem, boolean empty) {
                super.updateItem(logItem, empty);

                setText((!empty && logItem != null) ? logItem : null);
                getStyleClass().add("single_log");
                logLst.scrollTo(logManager.logProperty().size());
            }
        });
    }
}