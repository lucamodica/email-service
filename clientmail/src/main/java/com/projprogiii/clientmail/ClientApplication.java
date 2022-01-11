package com.projprogiii.clientmail;

import com.projprogiii.lib.objects.User;
import com.projprogiii.lib.utilities.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.*;

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

        try{

            //Creating the object
            User user = new User("luca.modica@unito.it");
            System.out.println(Util.validateEmail(user.email()));

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
        launch();
    }
}