package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.AlertManager;
import com.projprogiii.clientmail.utils.AlertText;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.utilities.Util;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComposeController extends Controller {

    @FXML
    private TextFlow warnAlert;
    @FXML
    private TextField senderTextField;
    @FXML
    private TextField recipientsTextField;
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
        //clearing all fields
        recipientsTextField.clear();
        objectTextField.clear();
        messageEditor.setHtmlText("");

        ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
    }

    @FXML
    private void onSendButtonClick(MouseEvent mouseEvent) {

        String[] recipentsArray = recipientsTextField.getText().split("\\s*,\\s*");
        System.out.println(Arrays.toString(recipentsArray));
        if (Arrays.stream(recipentsArray).allMatch(Util::validateEmail)){
            Email email = new Email(senderTextField.getText(),
                    new ArrayList<>(List.of(recipentsArray)),
                    objectTextField.getText(), messageEditor.getHtmlText());

            model.getClient().sendEmail(email);
            model.addEmail(email);

            //clearing all fields
            recipientsTextField.clear();
            objectTextField.clear();
            messageEditor.setHtmlText("");

            ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
        } else {
            //TODO better input error management and error alert
            System.out.println("ERRORE NELL'INSERIMENTO DELL'indirizzo MAIL, per favore ricontrollare");
            AlertManager.showTemporizedAlert(warnAlert, AlertText.INVALID_RECIPIENTS, 2);
        }
    }


}
