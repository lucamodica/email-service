package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.utilities.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.util.ArrayList;
import java.util.Arrays;

public class ComposeController extends Controller {

    @FXML
    private TextField senderTextField;
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
    public void initialize(){
        senderTextField.setEditable(false);
        senderTextField.setText(model.getClient().getUser().emailAddress());
    }

    @FXML
    private void onCancelButtonClick(MouseEvent mouseEvent) {
        ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
    }

    @FXML
    private void onSendButtonClick(MouseEvent mouseEvent) {
        boolean badAdress = false;
        ArrayList<String> recipentsArray = new ArrayList<>(Arrays.asList(recipientTextField.getText().split("\\s*,\\s*")));
        for (String adress : recipentsArray) {
            if (!Util.validateEmail(adress)){
                badAdress = true;
            }
        }
        if (!badAdress){
            Email email = new Email(senderTextField.getText(), recipentsArray, objectTextField.getText(), messageEditor.getHtmlText());
            model.getClient().sendEmail(email);
            model.addEmail(email);
            ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
        } else {
            //TODO better input error management and error alert
            System.out.println("ERRORE NELL'INSERIMENTO DELL'indirizzo MAIL, per favore ricontrollare");
        }
    }
}
