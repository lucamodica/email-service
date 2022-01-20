package com.projprogiii.servermail;

import com.projprogiii.servermail.model.Model;
import com.projprogiii.servermail.server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp extends Application {

    public static Server server;
    public static Model model;
    private static ExecutorService appFX;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(ServerApp.class.
                getResource("server_scene.fxml"));
        Scene scene = new Scene(loader.load(), 1080, 720);
        stage.setTitle("Mail Server");
        stage.setScene(scene);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.show();

    }

    @Override
    public void stop(){
        model.getLogManager().
                printSystemLog("Server shutting down...");
        server.interrupt();
        appFX.shutdown();
    }

    public static void main(String[] args) {
        model = Model.getInstance();
        server = Server.getInstance();
        appFX = Executors.newSingleThreadExecutor();

        appFX.execute(Application::launch);
        ((Thread) server).start();
    }

}