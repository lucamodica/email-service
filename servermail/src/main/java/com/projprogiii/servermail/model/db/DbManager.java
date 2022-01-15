package com.projprogiii.servermail.model.db;

public class DbManager {

    private DbManager(){

    }
    public static DbManager getInstance(){
        return new DbManager();
    }
}
