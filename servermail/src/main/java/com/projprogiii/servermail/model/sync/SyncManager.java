package com.projprogiii.servermail.model.sync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class used to handle concurrent access to the database from different threads/sessions, creating a ReentrantReadWriteLock for each user.
 * When a client send a request, an entry is created; when the client disconnects the entry is removed.
 */
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
