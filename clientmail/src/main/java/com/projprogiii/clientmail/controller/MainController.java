package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.AlertManager;
import com.projprogiii.clientmail.utils.AlertText;
import com.projprogiii.lib.objects.Email;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

import java.util.List;

public class MainController extends Controller {

    @FXML
    private ListView<Email> emailsLst;
    @FXML
    private Label fromLbl;
    @FXML
    private Label toLbl;
    @FXML
    private Label subjectLbl;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label DateLbl;
    @FXML
    private WebView emailContentTxt;
    @FXML
    public Button composeBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button forwardBtn;
    @FXML
    private SplitMenuButton replyBtn;
    @FXML
    private MenuItem replyAllBtn;

    @FXML
    private TextFlow dangerAlert;
    @FXML
    private TextFlow successAlert;

    private Email selectedEmail;
    private Email emptyEmail;


    @FXML
    public void initialize(){

        //istanza nuovo client
        model.generateRandomEmails(10);

        selectedEmail = null;

        //binding tra lstEmails e inboxProperty
        emailsLst.itemsProperty().bind(model.inboxProperty());
        emailsLst.setOnMouseClicked(this::showSelectedEmail);
        usernameLbl.textProperty().bind(model.emailAddressProperty());

        //TODO: test addEmail, to be deleted
        composeBtn.setOnMouseClicked(this::switchToCompose);

        emptyEmail = new Email("", List.of(""), "", "", null);
        updateDetailView(emptyEmail);
    }

    public TextFlow getSuccessAlert() {
        return successAlert;
    }

    /**
     * Delete the selected email
     */
    @FXML
    private void onDeleteButtonClick() {
        model.deleteEmail(selectedEmail);
        updateDetailView(emptyEmail);
        AlertManager.showTemporizedAlert(dangerAlert, AlertText.MESSAGE_DELETED, 2);
    }

    /**
     * Show the mail in the view
     */
    private void showSelectedEmail(MouseEvent mouseEvent) {
        Email email = emailsLst.getSelectionModel().getSelectedItem();
        if (email != null) {
            email.setToRead(false);
        }

        selectedEmail = email;
        updateDetailView(email);
    }
    /**
     * Update the view with the selected email
     */
    private void updateDetailView(Email email) {
        if(email != null) {
            fromLbl.setText(email.getSender());
            toLbl.setText(String.join(", ", email.getReceivers()));
            subjectLbl.setText(email.getSubject());
            emailContentTxt.getEngine().loadContent(email.getText());
            DateLbl.setText(email.dateToString());
        }
    }

    //TODO: listener test only, to be deleted
    private void addNewEmail(MouseEvent mouseEvent){
        model.addRandomEmail();
    }
    //TODO: listener test only, to be deleted
    private void switchToCompose(MouseEvent mouseEvent) {
        ClientApplication.sceneController.switchTo(SceneName.COMPOSE);
    }

}