package com.projprogiii.clientmail.scene;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.utils.AlertManager;
import com.projprogiii.clientmail.utils.AlertText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneController {

    private final HashMap<String, Pane> sceneMap;
    private final Scene main;

    private SceneController(Scene main){
        this.main = main;
        sceneMap = new HashMap<>();
    }
    public static SceneController getInstance(Scene main){
        return new SceneController(main);
    }

    public void addScene(String name) throws IOException {
        String path = name + '/' + name + "_scene.fxml";
        sceneMap.put(name, FXMLLoader.load(Objects.requireNonNull
                (ClientApplication.class.getResource(path)))
        );
    }

    public void switchTo(String name){
        main.setRoot(sceneMap.get(name));
    }
}
