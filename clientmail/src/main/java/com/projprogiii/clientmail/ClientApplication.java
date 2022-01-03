package com.projprogiii.clientmail;

import com.projprogiii.lib.model.Mail;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL clientUrl = ClientApplication.class.getResource("customlayout-client.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(clientUrl);
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Email client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}