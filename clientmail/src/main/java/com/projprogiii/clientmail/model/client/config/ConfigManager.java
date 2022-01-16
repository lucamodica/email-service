package com.projprogiii.clientmail.model.client.config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private final Properties prop;

    private ConfigManager() {
        prop = new Properties();
        File f = getDir();

        try{
            if (!f.exists()) {
                createPropertiesFile(f);
            } else {
                prop.load(new FileInputStream(f));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static ConfigManager getInstance(){
        return new ConfigManager();
    }

    private void createPropertiesFile(File path) throws IOException {
        prop.setProperty("user.emailAddress", "CHANGE_ME");
        prop.setProperty("user.server_host", "127.0.0.1");
        prop.setProperty("user.server_port", "42069");

        prop.store(new FileOutputStream(path),  null);
    }

    public String readProperty(String propName){
        return prop.getProperty(propName);
    }

    private File getDir() {
        String uri = new File("").getAbsolutePath() + "/clientmail/src/main/user.properties";
        return new File(uri);
    }
}
