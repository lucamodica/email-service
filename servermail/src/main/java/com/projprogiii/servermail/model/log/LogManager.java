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

    private final ListProperty<Log> log;
    private final ObservableList<Log> logContent;
    private final String serverName;


    private LogManager (){
        this.logContent = FXCollections.observableList(Collections.
                synchronizedList(new LinkedList<>()));
        this.log = new SimpleListProperty<>();
        this.log.set(logContent);

        this.serverName = "server";
    }

    public static LogManager getInstance(){
        return new LogManager();
    }


    public ListProperty<Log> logProperty() {
        return log;
    }


    private synchronized String getTimestamp(){
        return '[' + CommonUtil.formatDate(new Date()) + "] ";
    }


    public synchronized void printNewLine(){
        logContent.add(new Log(LogType.NORMAL, ""));
    }

    public synchronized void printLog(String logText, LogType logType){
        logContent.add(new Log(logType,
                getTimestamp() + serverName + " >> " + logText));
    }

    public synchronized void printSysLog(String logText){
        logContent.add(new Log(LogType.SYSOP,
                getTimestamp() + serverName + " << " + logText));
    }

    public synchronized void printError(String logText) {
        logContent.add(new Log(LogType.ERROR,
                getTimestamp() + logText));
    }
}
