package com.projprogiii.clientmail;

import com.projprogiii.clientmail.model.Model;
import com.projprogiii.clientmail.model.client.Client;
import com.projprogiii.clientmail.scene.SceneController;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.*;

public class ClientApp extends Application {

    public static Model model;
    public static SceneController sceneController;
    private static ExecutorService appFX;
    private static ScheduledExecutorService fetchEmails;
    private static Date lastFetch = new Date(Long.MIN_VALUE);

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

    @Override
    public void stop(){
        appFX.shutdown();

        fetchEmails.shutdown();
        try {
            System.out.println(fetchEmails.awaitTermination(1, TimeUnit.SECONDS) ?
                    "" : "Timeout elapsed before fetchEmails thread termination.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        model = Model.getInstance();
        appFX = Executors.newSingleThreadExecutor();
        fetchEmails = Executors.newSingleThreadScheduledExecutor();
        Client client = model.getClient();

        /*
        Email email = new Email(
                client.getUser(),
                Collections.singletonList("Enrico@unito.it"),
                "Hey!",
                "Hey there, test mail here"
        );
        Executors.newSingleThreadExecutor().execute(() -> {
            ServerResponse resp = client.sendCmd(CommandName.SEND_EMAIL, email);
            if (resp != null){
                Platform.runLater(() -> model.addEmail(email));
                email.setToRead(false);
            }
        });
         */

        //Start JavaFX app
        appFX.execute(Application::launch);

        //Start the fetch email thread
        fetchEmails.scheduleAtFixedRate(
                () -> {
                    ServerResponse resp = client.sendCmd(CommandName.FETCH_EMAIL,
                            lastFetch);

                    System.out.println(resp);
                    lastFetch = new Date();
                },1, 2, TimeUnit.SECONDS);
    }
}