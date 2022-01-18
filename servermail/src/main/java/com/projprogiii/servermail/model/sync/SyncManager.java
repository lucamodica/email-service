package com.projprogiii.servermail.model.sync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SyncManager {

    private ConcurrentHashMap<String, ReentrantReadWriteLock> locks;

    private SyncManager(){
        locks = new ConcurrentHashMap<>();
    }
    public static SyncManager getInstance(){
        return new SyncManager();
    }

    public void addLock(String user){
        locks.putIfAbsent(user, new ReentrantReadWriteLock());
    }
    public void removeLock(String user){
        locks.remove(user);
    }
}
