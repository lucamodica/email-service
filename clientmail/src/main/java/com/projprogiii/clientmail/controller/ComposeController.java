package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.AlertManager;
import com.projprogiii.clientmail.utils.AlertText;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.utilities.Util;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComposeController extends Controller {

    @FXML
    private TextFlow warnAlert;
    @FXML
    private TextFlow msgSentAlert;
    @FXML
    private TextField senderTextField;
    @FXML
    private TextField recipientsTextField;
    @FXML
    private TextField objectTextField;
    @FXML
    private HTMLEditor messageEditor;


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

            //TODO da valutare come procedere per conferma
            //msgSentAlert.setVisible(true);
            //ClientApplication.sceneController.switchTo(SceneName.MAIN.toString());
        } else {
            AlertManager.showTemporizedAlert(warnAlert, AlertText.INVALID_RECIPIENTS, 2);
        }
    }


}
