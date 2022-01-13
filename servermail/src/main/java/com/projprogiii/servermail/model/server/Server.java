package com.projprogiii.servermail.model.server;

import com.projprogiii.servermail.model.server.config.ConfigManager;

public class Server {

    private String threadsNumber;
    private String timeout;
    private int serverPort;

    private Server(){
        ConfigManager configManager = ConfigManager.getInstance();

        this.threadsNumber = configManager.
                readProperty("server.threads_number");
        this.timeout = configManager.
                readProperty("server.timeout");
        this.serverPort = Integer.parseInt(configManager.
                readProperty("server.server_port"));
    }
    public static Server getInstance(){
        return new Server();
    }
}
