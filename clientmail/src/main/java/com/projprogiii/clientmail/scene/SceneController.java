package com.projprogiii.clientmail.scene;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneController {
    private record ClientWindow(Pane pane, Controller controller) {}

    private final HashMap<SceneName, ClientWindow> sceneMap;
    private final Scene main;

    private SceneController(Scene main){
        this.main = main;
        sceneMap = new HashMap<>();
    }
    public static SceneController getInstance(Scene main){
        return new SceneController(main);
    }

    public void addScene(SceneName name) throws IOException {
        String path = name.toString() + '/' + name + "_scene.fxml";

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull
                (ClientApp.class.getResource(path)));
        sceneMap.put(name, new ClientWindow(loader.load(), loader.getController()));
    }

    public Controller getController(SceneName name){
        return sceneMap.get(name).controller();
    }

    public void switchTo(SceneName name){
        main.setRoot(sceneMap.get(name).pane());
    }

}
