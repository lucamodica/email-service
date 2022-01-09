package com.projprogiii.clientmail;

import com.projprogiii.lib.objects.User;
import com.projprogiii.lib.utilities.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

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
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();


        User user = new User("luca.modica@unito.it");
        System.out.println(Util.validateEmail(user.email()));
    }

    public static void main(String[] args) {
        launch();
    }
}