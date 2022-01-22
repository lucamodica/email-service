package com.projprogiii.servermail.controller;

import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.Log;
import com.projprogiii.servermail.model.log.LogManager;
import com.projprogiii.servermail.model.log.LogType;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ServerController {

    @FXML
    private ListView<Log> logLst;

    public void initialize(){
        LogManager logManager = ServerApp.model.getLogManager();

        logLst.itemsProperty().bind(logManager.logProperty());
        logLst.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Log logItem, boolean empty) {
                super.updateItem(logItem, empty);

                setText((!empty && logItem != null) ? logItem.toString() : null);
                if (logItem != null) {
                    if (logItem.type().equals(LogType.NORMAL)){
                        getStyleClass().add("normal_log");
                    } else if (logItem.type().equals(LogType.SYSOP)){
                        getStyleClass().add("sysop_log");
                    } else {
                        getStyleClass().add("error_log");
                    }
                }

                getStyleClass().add("log");
                logLst.scrollTo(logManager.logProperty().size());
            }
        });
    }
}