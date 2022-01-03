package com.projprogiii.clientmail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                ClientApplication.class.getResource("main/main_scene.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("ClientMail");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}