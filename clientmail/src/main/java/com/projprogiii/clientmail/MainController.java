package com.projprogiii.clientmail;

import com.projprogiii.lib.objects.Mail;
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
    private ListView<Mail> lstEmails;

    private ClientModel model;
    private Mail selectedEmail;
    private Mail emptyEmail;

    @FXML
    public void initialize(){

        if (this.model != null)
            throw new IllegalStateException("Model can only be initialized once");

        //istanza nuovo client
        model = new ClientModel("studente@unito.it");
        model.generateRandomEmails(10);

        selectedEmail = null;

        //binding tra lstEmails e inboxProperty
        lstEmails.itemsProperty().bind(model.inboxProperty());
        lstEmails.setOnMouseClicked(this::showSelectedEmail);
        lblUsername.textProperty().bind(model.emailAddressProperty());

        emptyEmail = new Mail("", List.of(""), "", "");

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
        Mail email = lstEmails.getSelectionModel().getSelectedItem();

        selectedEmail = email;
        updateDetailView(email);
    }

    /**
     * Aggiorna la vista con la mail selezionata
     */
    protected void updateDetailView(Mail email) {
        if(email != null) {
            lblFrom.setText(email.getSender());
            lblTo.setText(String.join(", ", email.getReceivers()));
            lblSubject.setText(email.getSubject());
            txtEmailContent.getEngine().loadContent(email.getText());
        }
    }

}