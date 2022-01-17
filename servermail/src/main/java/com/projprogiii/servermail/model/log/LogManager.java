package com.projprogiii.servermail.model.log;

import com.projprogiii.lib.utils.CommonUtil;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class LogManager {

    private ListProperty<String> log;
    private ObservableList<String> logContent;

    private static String serverName;

    private LogManager (){
        this.logContent = FXCollections.observableList(Collections.
                synchronizedList(new LinkedList<>()));
        this.log = new SimpleListProperty<>();
        this.log.set(logContent);

        this.serverName = " server> ";
    }
    public static LogManager getInstance(){
        return new LogManager();
    }

    public ListProperty<String> logProperty() {
        return log;
    }

    public void addLog(String logText){
        logContent.add(logText);
    }
    public void printLog(String logText){
        addLog('[' + CommonUtil.formatDate(new Date()) + ']'
                + logText);
    }
    public void printSystemLog(String logText){
        printLog(serverName + logText);
    }
}
