package com.projprogiii.clientmail;

import com.projprogiii.clientmail.model.Model;
import com.projprogiii.clientmail.scene.SceneController;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.lib.objects.User;
import com.projprogiii.lib.utilities.Util;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;

public class ClientApplication extends Application {

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

        //TODO: Email file writing test only. To be deleted
        try{

            //Creating the object
            User user = new User("luca.modica@unito.it");
            System.out.println(Util.validateEmail(user.emailAddress()));

            //Creating stream and writing the object
            FileOutputStream fout = new FileOutputStream("f.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(user);
            out.flush();

            FileInputStream fin = new FileInputStream("f.txt");
            ObjectInputStream in = new ObjectInputStream(fin);

            User a = (User) in.readObject();
            System.out.println(a);

            //closing the stream
            out.close();
            System.out.println("success");
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        model = Model.getInstance();
        launch();
    }
}