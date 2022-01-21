package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApp;
import com.projprogiii.clientmail.scene.SceneName;
import com.projprogiii.clientmail.utils.alert.AlertManager;
import com.projprogiii.clientmail.utils.alert.AlertText;
import com.projprogiii.clientmail.utils.responsehandler.ResponseHandler;
import com.projprogiii.lib.enums.CommandName;
import com.projprogiii.lib.objects.Email;
import com.projprogiii.lib.objects.ServerResponse;
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
        emptyEmail = Email.generateEmptyEmail();
        selectedEmail = emptyEmail;

        //binding tra lstEmails e inboxProperty
        emailsLst.itemsProperty().bind(model.getInboxProperty());
        setListViewCellsListeners(emailsLst);
        usernameLbl.textProperty().bind(model.getEmailAddressProperty());

        setBtnsListeners();
        updateDetailView(emptyEmail);
    }

    public TextFlow getSuccessAlert() { return successAlert; }
    public TextFlow getDangerAlert() { return dangerAlert; }

    private void setBtnsListeners(){
        deleteBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) -> delete()));

        forwardBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) -> forward()));

        replyBtn.setOnMouseClicked(event ->
                opButtonHandler(event, (OnButtonClick) -> reply()));

        replyAllBtn.setOnAction(event ->
                opButtonHandler(null, (OnButtonClick) -> replyAll()));
    }

    private void delete(){
        //server-side delete
        ServerResponse response = model.getClient()
                .sendCmd(CommandName.DELETE_EMAIL, selectedEmail);
        ResponseHandler.handleResponse(response,
                ClientApp.sceneController.getController(SceneName.MAIN),
                () -> {
                    //client-side delete
                    model.deleteEmail(selectedEmail);
                    updateDetailView(emptyEmail);
                    AlertManager.showTemporizedAlert(dangerAlert,
                            AlertText.MESSAGE_DELETED, 2);
                });
        selectedEmail = emptyEmail;
    }

    private void forward(){
        composeFieldsSetter("",
                "Forward: " + selectedEmail.getSubject(),
                selectedEmail.getText());
    }

    private void reply(){
        composeFieldsSetter(selectedEmail.getSender(),
                "Reply: " + selectedEmail.getSubject(),
                "");
    }

    private void replyAll(){
        replyBtn.hide();
        List<String> list = selectedEmail.getReceivers();
        list.remove(getUserEmail());
        composeFieldsSetter(selectedEmail.getSender() +
                        CommonUtil.receiversToString(list),
                "ReplyAll: " + selectedEmail.getSubject(),
                "");
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

    //Used to automatically set textfields in reply/replyAll and forward cases
    @FXML
    private void composeFieldsSetter(String receivers, String object, String htmltext){
        ClientApp.sceneController.switchTo(SceneName.COMPOSE);
        ComposeController controller = (ComposeController) ClientApp.sceneController.
                getController(SceneName.COMPOSE);

        controller.getSenderTextField().setText(getUserEmail());
        controller.getRecipientsTextField().setText(receivers);
        controller.getObjectTextField().setText(object);
        controller.getMessageEditor().setHtmlText(htmltext);
    }
    //anonymous class used to manage bold text in function of Email's isToRead state
    private void setListViewCellsListeners(ListView<Email> emailsLst){
        emailsLst.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Email email, boolean empty) {
                super.updateItem(email, empty);

                boolean check = !empty && email != null;

                String text = !check ? null :
                        Objects.equals(email.getSender(), getUserEmail()) ?
                        "YOU - " + email.getSubject() :
                        email.toString();

                setText(text);
                setStyle(check && email.isToRead() ? "-fx-font-weight: bold" : null);

                setOnMouseClicked((click) -> {
                    Email selectedEmail = emailsLst.getSelectionModel().getSelectedItem();
                    if (selectedEmail != null && selectedEmail.isToRead()) {
                        //client-side markAsRead
                        selectedEmail.setToRead(false);
                        setStyle(null);

                        //server-side markAsRead
                        ServerResponse response = model.getClient().sendCmd(CommandName.MARK_AS_READ, selectedEmail);
                        ResponseHandler.handleResponse(response,
                                ClientApp.sceneController.getController(SceneName.MAIN),
                                () -> {}
                                );
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