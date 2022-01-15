package com.projprogiii.servermail;

import com.projprogiii.lib.utils.CommonUtil;
import com.projprogiii.servermail.model.Model;
import com.projprogiii.servermail.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Date;

public class ServerApp extends Application {

    public static Server server;
    public static Model model;


    @Override
    public void start(Stage stage) throws IOException {

        model = Model.getInstance();
        server = Server.getInstance();

        FXMLLoader loader = new FXMLLoader(ServerApp.class.
                getResource("server_scene.fxml"));
        Scene scene = new Scene(loader.load(), 1080, 720);
        stage.setTitle("Mail Server");
        stage.setScene(scene);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}