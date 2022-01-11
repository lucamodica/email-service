package com.projprogiii.clientmail;

import com.projprogiii.clientmail.model.Model;
import com.projprogiii.lib.objects.User;
import com.projprogiii.lib.utilities.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;

public class ClientApplication extends Application {

    public static Model model;

    //A Stage object to keep track of it ad change its
    //scene once is necessary
    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {

        currentStage = stage;
        stage.setTitle("ClientMail");
        switchSceneTo("main");

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

    public static void switchSceneTo(String sceneName) throws IOException {

        String path = sceneName + '/' + sceneName + "_scene.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(path));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void main(String[] args) {
        model = Model.getInstance();
        launch();
    }
}