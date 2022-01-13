package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.AlertManager;
import com.projprogiii.clientmail.utils.AlertText;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.utilities.Util;
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
    private SplitMenuButton replyBtn;
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
        emptyEmail = new Email("", List.of(""), "", "", null);
        updateDetailView(emptyEmail);
    }

    public TextFlow getSuccessAlert() { return successAlert; }

    @FXML
    private void onComposeButtonClick() {
        ClientApplication.sceneController.switchTo(SceneName.COMPOSE);
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

    @FXML
    private void onForwardButtonClick() {
        fieldsSetter("",
                "Forward: " + selectedEmail.getSubject(),
                selectedEmail.getText());
    }

    @FXML
    private void onReplyButtonClick() {
        fieldsSetter(selectedEmail.getSender(),
                "Reply: " + selectedEmail.getSubject(),
                "");
    }

    @FXML
    private void onReplyAllButtonClick() {
        replyBtn.hide();

        List<String> list = selectedEmail.getReceivers();
        list.remove(model.getClient().getUser().emailAddress());

        fieldsSetter(selectedEmail.getSender() + Util.receiversToString(list),
                "ReplyAll: " + selectedEmail.getSubject(),
                "");
    }

    @FXML
    private void fieldsSetter(String receivers, String object, String htmltext){
        ClientApplication.sceneController.switchTo(SceneName.COMPOSE);
        ComposeController controller = (ComposeController) ClientApplication.sceneController.
                getController(SceneName.COMPOSE);

        controller.getSenderTextField().setText(model.getClient().getUser().emailAddress());
        controller.getRecipientsTextField().setText(receivers);
        controller.getObjectTextField().setText(object);
        controller.getMessageEditor().setHtmlText(htmltext);
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

    private void switchToForward(MouseEvent mouseEvent) {
        ClientApplication.sceneController.switchTo(SceneName.COMPOSE);
    }
}