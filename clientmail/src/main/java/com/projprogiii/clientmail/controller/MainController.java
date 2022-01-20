package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.alert.AlertManager;
import com.projprogiii.clientmail.utils.alert.AlertText;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.utils.CommonUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

import java.util.List;

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
        emptyEmail = Email.generateEmptyEmail();
        selectedEmail = emptyEmail;

        //binding tra lstEmails e inboxProperty
        emailsLst.itemsProperty().bind(model.inboxProperty());
        setListViewCellsListeners(emailsLst);
        usernameLbl.textProperty().bind(model.emailAddressProperty());

        setBtnsListeners();
        updateDetailView(emptyEmail);
    }

    public TextFlow getSuccessAlert() { return successAlert; }

    private void setBtnsListeners(){
        deleteBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) -> {
                    model.deleteEmail(selectedEmail);
                    selectedEmail = emptyEmail;
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
                    list.remove(model.getClient().getUser());
                    composeFieldsSetter(selectedEmail.getSender() +
                                    CommonUtil.receiversToString(list),
                            "ReplyAll: " + selectedEmail.getSubject(),
                            "");
                }));
    }
    private void opButtonHandler(MouseEvent mouseEvent, OnButtonClick handler){
        if (!Email.isEmpty(selectedEmail)){
            handler.handle(mouseEvent);
        }
    }

    @FXML
    private void onComposeButtonClick() {
        ClientApp.sceneController.switchTo(SceneName.COMPOSE);
    }
    @FXML
    private void composeFieldsSetter(String receivers, String object, String htmltext){
        ClientApp.sceneController.switchTo(SceneName.COMPOSE);
        ComposeController controller = (ComposeController) ClientApp.sceneController.
                getController(SceneName.COMPOSE);

        controller.getSenderTextField().setText(model.getClient().getUser());
        controller.getRecipientsTextField().setText(receivers);
        controller.getObjectTextField().setText(object);
        controller.getMessageEditor().setHtmlText(htmltext);
    }

    private void setListViewCellsListeners(ListView<Email> emailsLst){
        emailsLst.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Email email, boolean empty) {
                super.updateItem(email, empty);

                boolean check = !empty && email != null;
                setText(check ? email.toString() : null);
                setStyle(check && email.isToRead() ? "-fx-font-weight: bold" : null);

                setOnMouseClicked((click) -> {
                    Email selectedEmail = emailsLst.getSelectionModel().getSelectedItem();
                    if (selectedEmail != null) {
                        selectedEmail.setToRead(false);
                        setStyle(null);
                    }
                    MainController.this.selectedEmail = selectedEmail;
                    updateDetailView(selectedEmail);

                });
            }
        });
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