package com.projprogiii.clientmail.model.components;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ConfigManager {

    Properties prop;

    private ConfigManager(){
    }
    public static ConfigManager getInstance(){
        return new ConfigManager();
    }

    private void createPropertiesFile(){

    }

    public String readProperty(String propName){
        return prop.getProperty(propName);
    }

    private File getDir() {
        try {
            URI uri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            return new File(uri).getParentFile();
        } catch (URISyntaxException ignored) {
            return new File(".");
        }
    }
}
