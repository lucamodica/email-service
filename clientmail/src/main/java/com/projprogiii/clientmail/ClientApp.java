package com.projprogiii.clientmail;

import com.projprogiii.clientmail.model.Model;
import com.projprogiii.clientmail.model.client.Client;
import com.projprogiii.clientmail.scene.SceneController;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.alert.AlertManager;
import com.projprogiii.clientmail.utils.alert.AlertText;
import com.projprogiii.clientmail.utils.responsehandler.ResponseHandler;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;
import java.util.Date;
import java.util.List;
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

    //filters possible duplicates and new emails by date, then adds them to the ObservableList
    private static void fetch(ServerResponse resp){
        List<Email> l = resp.args()
                .stream()
                .filter(email -> !model.getInboxContent().contains(email))
                .toList();
        if (!l.isEmpty()){
            Platform.runLater(() -> model.addEmails(l));
            if (!lastFetch.equals(new Date(Long.MIN_VALUE))){
                AlertManager.showSuccessSendMessage(AlertText.NEW_EMAILS, 2);
            }
        }
        lastFetch = new Date();
    }

    public static void main(String[] args) {
        model = Model.getInstance();
        appFX = Executors.newSingleThreadExecutor();
        fetchEmails = Executors.newSingleThreadScheduledExecutor();
        Client client = model.getClient();

        //Start JavaFX app using method reference
        appFX.execute(Application::launch);

        //Start the fetch email thread; operation repeated at fixed rate for constant fetching

        fetchEmails.scheduleAtFixedRate(
                () -> {
                    ServerResponse resp = client.sendCmd(CommandName.FETCH_EMAIL,
                            lastFetch);

                    if (sceneController != null) {
                        ResponseHandler.handleResponse(resp,
                                sceneController.getController(SceneName.MAIN),
                                () -> fetch(resp));
                    }
                },1, 5, TimeUnit.SECONDS);
    }
}