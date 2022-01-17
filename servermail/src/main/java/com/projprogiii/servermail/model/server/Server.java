package com.projprogiii.servermail.model.server;

import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.LogManager;
import com.projprogiii.servermail.model.server.config.ConfigManager;
import com.projprogiii.servermail.model.server.session.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    private final int threadsNumber;
    private final int timeout;
    private final int serverPort;
    public final LogManager logManager;

    private Server(){
        ConfigManager configManager = ConfigManager.getInstance();
        logManager = ServerApp.model.getLogManager();

        logManager.printSystemLog("Loading initial configuration server.properties...");

        this.threadsNumber = Integer.parseInt(configManager.
                readProperty("server.threads_number"));
        this.timeout = Integer.parseInt(configManager.
                readProperty("server.timeout"));
        this.serverPort = Integer.parseInt(configManager.
                readProperty("server.server_port"));

        printLogInit();
    }

    private void printLogInit(){
        logManager.printSystemLog("Configuration loaded.");
        logManager.printSystemLog("Max thread in thread pool: " +
                threadsNumber + ".");
        logManager.printSystemLog("Timeout to accept a new connection: " +
                timeout + " ms.");
        logManager.printSystemLog("Server port: " + serverPort + ".");
        logManager.printLog("");
        logManager.printSystemLog("Hello!");
    }

    public static Server getInstance(){
        return new Server();
    }

    public int getTimeout() {
        return timeout;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void startSession(){
        ExecutorService exec = Executors.newFixedThreadPool(threadsNumber);
        Runnable task = new Session();
        exec.execute(task);

        //useful for waiting before termination of server. either waiting n seconds or thread interruption or completed execution
        try {
            exec.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
