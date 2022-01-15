package com.projprogiii.servermail.model;

import com.projprogiii.servermail.model.db.DbManager;
import com.projprogiii.servermail.model.log.LogManager;
import com.projprogiii.servermail.model.sync.SyncManager;


public class Model {

    private final DbManager dbManager;
    private final SyncManager syncManager;
    private final LogManager logManager;

    private Model(){
        dbManager = DbManager.getInstance();
        syncManager = SyncManager.getInstance();
        logManager = LogManager.getInstance();
    }
    public static Model getInstance(){
        return new Model();
    }

    public DbManager getDbManager() {
        return dbManager;
    }

    public SyncManager getSyncManager() {
        return syncManager;
    }

    public LogManager getLogManager() {
        return logManager;
    }
}
