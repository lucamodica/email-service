package com.projprogiii.servermail.server;

import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.model.log.LogManager;
import com.projprogiii.servermail.server.config.ConfigManager;

public class Server {

    private final String threadsNumber;
    private final String timeout;
    private final int serverPort;
    private final LogManager logManager;

    private Server(){
        ConfigManager configManager = ConfigManager.getInstance();
        logManager = ServerApp.model.getLogManager();

        logManager.printSystemLog("Loading initial configuration server.properties...");

        this.threadsNumber = configManager.
                readProperty("server.threads_number");
        this.timeout = configManager.
                readProperty("server.timeout");
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
}
