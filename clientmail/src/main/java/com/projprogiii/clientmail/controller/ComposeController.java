package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.lib.objects.Email;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComposeController extends Controller {

    @FXML
    private TextField recipientTextField;
    @FXML
    private TextField objectTextField;
    @FXML
    private HTMLEditor messageEditor;
    @FXML
    private Button sendBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label lblsenderEmailAdress;


    @FXML
    public void initialize(){
        lblsenderEmailAdress.setText(model.getClient().getUser().emailAddress());
        recipientTextField = new TextField();
        objectTextField = new TextField();
        messageEditor = new HTMLEditor();



    }

    @FXML
    private void onCancelButtonClick(MouseEvent mouseEvent) {
        ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
    }

    @FXML
    private void onSendButtonClick(MouseEvent mouseEvent) {
        /*List<String> recipentsArray = Arrays.asList(recipientTextField.getText().split("\\s*,\\s*"));
        System.out.println("in");
        String obj = (String)objectTextField.getText();
        System.out.println(lblsenderEmailAdress.getText());
        System.out.println(obj);
        System.out.println(recipentsArray);
        System.out.println(messageEditor.getHtmlText());*/

        System.out.println("inCompose");


        //Email email = new Email(lblsenderEmailAdress.getText(), recipentsArray, objectTextField.getText(), messageEditor.getHtmlText());
        //model.addEmail(email);
        //TODO implement sending email communication
        //ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
    }
}
