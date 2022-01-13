package com.projprogiii.servermail.server;

import com.projprogiii.servermail.ServerApp;
import com.projprogiii.servermail.server.config.ConfigManager;

public class Server {

    private final String threadsNumber;
    private final String timeout;
    private final int serverPort;

    private Server(){
        ConfigManager configManager = ConfigManager.getInstance();

        ServerApp.printSystemLog("Loading initial configuration server.properties...");

        this.threadsNumber = configManager.
                readProperty("server.threads_number");
        this.timeout = configManager.
                readProperty("server.timeout");
        this.serverPort = Integer.parseInt(configManager.
                readProperty("server.server_port"));

        printLogInit();
    }
    private void printLogInit(){
        ServerApp.printSystemLog("Configuration loaded.");
        ServerApp.printSystemLog("Max thread in thread pool: " +
                threadsNumber + ".");
        ServerApp.printSystemLog("Timeout to accept a new connection: " +
                timeout + " ms.");
        ServerApp.printSystemLog("Server port: " + serverPort + ".");
        ServerApp.printLog("");
        ServerApp.printSystemLog("Hello!");
    }
    public static Server getInstance(){
        return new Server();
    }
}
