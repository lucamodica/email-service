package com.projprogiii.servermail.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.LinkedList;


public class Model {

    private ListProperty<String> log;
    private ObservableList<String> logContent;

    /**
     * Class constructor and getInstance function.
     */
    private Model(){
        this.logContent = FXCollections.observableList(Collections.
                synchronizedList(new LinkedList<>()));
        this.log = new SimpleListProperty<>();
        this.log.set(logContent);
    }
    public static Model getInstance(){
        return new Model();
    }

    public ListProperty<String> logProperty() {
        return log;
    }

    public void addLog(String logText){
        logContent.add(logText);
    }
}
