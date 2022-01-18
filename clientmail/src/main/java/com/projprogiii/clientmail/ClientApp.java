package com.projprogiii.clientmail;

import com.projprogiii.clientmail.model.Model;
import com.projprogiii.clientmail.scene.SceneController;
import com.projprogiii.clientmail.scene.SceneName;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientApp extends Application {

    public static Model model;
    public static SceneController sceneController;

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(new Pane(), 1280, 720);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        sceneController = SceneController.getInstance(scene);
        sceneController.addScene(SceneName.MAIN);
        sceneController.addScene(SceneName.COMPOSE);

        stage.setTitle("ClientMail");
        sceneController.switchTo(SceneName.MAIN);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        model = Model.getInstance();

        //Thread t = new Thread(()->launch());
        //t.start();
        launch();
        model.getClient().login();

    }
}