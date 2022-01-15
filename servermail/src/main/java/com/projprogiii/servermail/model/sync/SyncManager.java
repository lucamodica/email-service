package com.projprogiii.servermail.model.sync;

import com.projprogiii.lib.objects.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SyncManager {

    private ConcurrentHashMap<User, ReentrantReadWriteLock> locks;

    private SyncManager(){
        locks = new ConcurrentHashMap<>();
    }
    public static SyncManager getInstance(){
        return new SyncManager();
    }

}
