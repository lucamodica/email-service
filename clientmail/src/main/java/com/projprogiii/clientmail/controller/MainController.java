package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.ClientApplication;
import com.projprogiii.clientmail.model.Model;
import com.projprogiii.lib.objects.Email;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.List;


public class MainController extends Controller {

    public BorderPane pnlReadMessage;
    public BorderPane pnlEmailList;
    public Button deleteBtn;
    public Button forwardBtn;
    public SplitMenuButton replyBtn;
    public MenuItem replyAllBtn;

    @FXML
    public Button composeBtn;

    @FXML
    private Label lblFrom;

    @FXML
    private Label lblTo;

    @FXML
    private Label lblSubject;

    @FXML
    private Label lblUsername;

    @FXML
    private WebView txtEmailContent;

    @FXML
    private Label lblDate;

    @FXML
    private ListView<Email> lstEmails;

    private Email selectedEmail;
    private Email emptyEmail;

    @FXML
    public void initialize(){

        //istanza nuovo client
        model.generateRandomEmails(10);

        selectedEmail = null;

        //binding tra lstEmails e inboxProperty
        lstEmails.itemsProperty().bind(model.inboxProperty());
        lstEmails.setOnMouseClicked(this::showSelectedEmail);
        lblUsername.textProperty().bind(model.emailAddressProperty());

        //TODO: test addEmail, to be deleted
        composeBtn.setOnMouseClicked(this::switchToCompose);

        emptyEmail = new Email("", List.of(""), "", "", null);
        updateDetailView(emptyEmail);
    }

    /**
     * Elimina la mail selezionata
     */
    @FXML
    protected void onDeleteButtonClick() {
        model.deleteEmail(selectedEmail);
        updateDetailView(emptyEmail);
    }

    /**
     * Mostra la mail selezionata nella vista
     */
    protected void showSelectedEmail(MouseEvent mouseEvent) {
        Email email = lstEmails.getSelectionModel().getSelectedItem();
        if (email != null) {
            email.setToRead(false);
        }

        selectedEmail = email;
        updateDetailView(email);
    }

    //TODO: listener test only, to be deleted
    private void addNewEmail(MouseEvent mouseEvent){
        model.addRandomEmail();
    }

    //TODO: listener test only, to be deleted
    private void switchToCompose(MouseEvent mouseEvent) {
        try{
            ClientApplication.switchSceneTo("compose");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Cannot switch to the compose window!");
        }
    }

    /**
     * Aggiorna la vista con la mail selezionata
     */
    protected void updateDetailView(Email email) {
        if(email != null) {
            lblFrom.setText(email.getSender());
            lblTo.setText(String.join(", ", email.getReceivers()));
            lblSubject.setText(email.getSubject());
            txtEmailContent.getEngine().loadContent(email.getText());
            lblDate.setText(email.dateToString());
        }
    }
}