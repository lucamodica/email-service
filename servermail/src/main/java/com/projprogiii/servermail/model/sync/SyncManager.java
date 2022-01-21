package com.projprogiii.servermail.model.sync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SyncManager {

    private final ConcurrentHashMap<String, ReentrantReadWriteLock> locks;

    private SyncManager(){
        locks = new ConcurrentHashMap<>();
    }
    public static SyncManager getInstance(){
        return new SyncManager();
    }


    public void addLockEntry(String user){
        locks.putIfAbsent(user, new ReentrantReadWriteLock());
    }

    public void removeLockEntry(String user){
        locks.remove(user);
    }

    public ReentrantReadWriteLock getLock(String user){
        return locks.get(user);
    }
}
