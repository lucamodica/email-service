package com.projprogiii.servermail.model;

import com.projprogiii.lib.objects.Email;
import com.projprogiii.servermail.model.server.Server;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.controlsfx.control.PropertySheet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Model {

    private Server server;

    private MapProperty<String, String> log;
    private ObservableMap<String, String> logContent;


    /**
     * Class constructor and getInstance function.
     */
    private Model(){
        this.server = Server.getInstance();

        this.logContent = FXCollections.observableMap(new ConcurrentHashMap<>());
        this.log = new SimpleMapProperty<>();
        this.log.set(logContent);
    }
    public static Model getInstance(){
        return new Model();
    }

    public MapProperty<String, String> logProperty() {
        return log;
    }
}
