package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.alert.AlertManager;
import com.projprogiii.clientmail.utils.alert.AlertText;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.enums.ServerResponseName;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
import com.projprogiii.lib.utils.CommonUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComposeController extends Controller {

    @FXML
    private TextFlow dangerAlert;
    @FXML
    private TextFlow successAlert;
    @FXML
    private TextField senderTextField;
    @FXML
    private TextField recipientsTextField;
    @FXML
    private TextField objectTextField;
    @FXML
    private HTMLEditor messageEditor;

    public TextFlow getSuccessAlert() { return successAlert; }
    public TextFlow getDangerAlert() { return dangerAlert; }

    @FXML
    public void initialize(){
        senderTextField.setEditable(false);
        senderTextField.setText(model.getClient().getUser());
    }

    public TextField getSenderTextField() {
        return senderTextField;
    }
    public TextField getRecipientsTextField() {
        return recipientsTextField;
    }
    public TextField getObjectTextField() {
        return objectTextField;
    }
    public HTMLEditor getMessageEditor() {
        return messageEditor;
    }

    @FXML
    private void onCancelButtonClick() {
        //clearing all fields
        recipientsTextField.clear();
        objectTextField.clear();
        messageEditor.setHtmlText("");

        ClientApp.sceneController.switchTo(SceneName.MAIN);
    }

    @FXML
    private void onSendButtonClick() {

        String[] recipentsArray = recipientsTextField.getText().split("\\s*,\\s*");
        if (Arrays.stream(recipentsArray).allMatch(CommonUtil::validateEmail)){
            Email email = new Email(senderTextField.getText(),
                    new ArrayList<>(List.of(recipentsArray)),
                    objectTextField.getText(), messageEditor.getHtmlText());

            //TODO: insert sendCmd method
            ServerResponse response = ClientApp.model.getClient().sendCmd(CommandName.SEND_EMAIL, email);
            if (response.responseName() == ServerResponseName.SUCCESS) {
                model.addEmails(Collections.singletonList(email));
                //clearing all fields
                recipientsTextField.clear();
                objectTextField.clear();
                messageEditor.setHtmlText("");

                AlertManager.showSuccessSendMessage(AlertText.MESSAGE_SENT, 2);
            } else {
                AlertManager.showTemporizedAlert(dangerAlert, AlertText.INVALID_RECIPIENTS, 2);
            }
        } else {
            AlertManager.showTemporizedAlert(dangerAlert, AlertText.INVALID_RECIPIENTS, 2);
        }
    }
}
