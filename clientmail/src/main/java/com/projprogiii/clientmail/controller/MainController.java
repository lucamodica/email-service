package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.AlertManager;
import com.projprogiii.clientmail.utils.AlertText;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.utils.CommonUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

import java.util.List;
import java.util.Objects;

public class MainController extends Controller {

    //Interface to generalize operation buttons handlers
    private interface OnButtonClick{
        void handle(MouseEvent mouseEvent);
    }


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
    private TextFlow dangerAlert;
    @FXML
    private TextFlow successAlert;

    @FXML
    private Button deleteBtn;
    @FXML
    private Button forwardBtn;
    @FXML
    private MenuItem replyAllBtn;
    @FXML
    private SplitMenuButton replyBtn;

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
        
        //Click listeners for the email operation buttons
        deleteBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) -> {
                    model.deleteEmail(selectedEmail);
                    updateDetailView(emptyEmail);
                    AlertManager.showTemporizedAlert(dangerAlert,
                            AlertText.MESSAGE_DELETED, 2);
                }));
        forwardBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) ->
                        composeFieldsSetter("",
                        "Forward: " + selectedEmail.getSubject(),
                        selectedEmail.getText())));
        replyBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) ->
                        composeFieldsSetter(selectedEmail.getSender(),
                        "Reply: " + selectedEmail.getSubject(),
                        "")));
        replyAllBtn.setOnAction(event ->
                opButtonHandler(null, (OnButtonClick) -> {
                    replyBtn.hide();
                    List<String> list = selectedEmail.getReceivers();
                    list.remove(model.getClient().getUser().emailAddress());
                    composeFieldsSetter(selectedEmail.getSender() +
                                    CommonUtil.receiversToString(list),
                            "ReplyAll: " + selectedEmail.getSubject(),
                            "");
                }));

        emptyEmail = new Email("", List.of(""), "", "", null);
        updateDetailView(emptyEmail);
    }

    public TextFlow getSuccessAlert() { return successAlert; }

    @FXML
    private void onComposeButtonClick() {
        ClientApp.sceneController.switchTo(SceneName.COMPOSE);
    }
    

    private void opButtonHandler(MouseEvent mouseEvent, OnButtonClick handler){
        try {
            if (!Email.isEmpty(Objects.requireNonNull(selectedEmail))){
                handler.handle(mouseEvent);
            }
        } catch (NullPointerException e){
            System.out.println("Cannot perform operation on an email " +
                    "if it's null");
        }
    }

    @FXML
    private void composeFieldsSetter(String receivers, String object, String htmltext){
        ClientApp.sceneController.switchTo(SceneName.COMPOSE);
        ComposeController controller = (ComposeController) ClientApp.sceneController.
                getController(SceneName.COMPOSE);

        controller.getSenderTextField().setText(model.getClient().getUser().emailAddress());
        controller.getRecipientsTextField().setText(receivers);
        controller.getObjectTextField().setText(object);
        controller.getMessageEditor().setHtmlText(htmltext);
    }

    private void showSelectedEmail(MouseEvent mouseEvent) {
        Email email = emailsLst.getSelectionModel().getSelectedItem();
        if (email != null) {
            email.setToRead(false);
        }
        selectedEmail = email;
        updateDetailView(email);
    }
    private void updateDetailView(Email email) {
        if(email != null) {
            fromLbl.setText(email.getSender());
            toLbl.setText(String.join(", ", email.getReceivers()));
            subjectLbl.setText(email.getSubject());
            emailContentTxt.getEngine().loadContent(email.getText());
            DateLbl.setText(email.dateToString());
        }
    }
}