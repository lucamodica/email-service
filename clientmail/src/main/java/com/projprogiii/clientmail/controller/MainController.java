package com.projprogiii.clientmail.controller;

import com.projprogiii.clientmail.model.Model;
import com.projprogiii.lib.objects.Email;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.util.List;

public class MainController {

    public BorderPane pnlReadMessage;
    public BorderPane pnlEmailList;
    public Button deleteBtn;
    public Button forwardBtn;
    public SplitMenuButton replyBtn;
    public MenuItem replyAllBtn;
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

    private Model model;
    private Email selectedEmail;
    private Email emptyEmail;

    @FXML
    public void initialize(){

        if (this.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        //istanza nuovo client
        model = new Model("studente@unito.it");
        model.generateRandomEmails(10);

        selectedEmail = null;

        //binding tra lstEmails e inboxProperty
        lstEmails.itemsProperty().bind(model.inboxProperty());
        lstEmails.setOnMouseClicked(this::showSelectedEmail);
        lblUsername.textProperty().bind(model.emailAddressProperty());

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

        selectedEmail = email;
        updateDetailView(email);
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