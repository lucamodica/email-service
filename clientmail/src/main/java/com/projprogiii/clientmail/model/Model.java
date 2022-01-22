package com.projprogiii.clientmail.model;

import com.projprogiii.clientmail.model.client.Client;
import com.projprogiii.lib.objects.Email;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;

public class Model {

    private final Client client;

    private final ListProperty<Email> inbox;
    private final ObservableList<Email> inboxContent;
    private final StringProperty emailAddress;

    private Model() {
        client = Client.getInstance();
        this.inboxContent = FXCollections.observableList(Collections.
                synchronizedList(new ArrayList<>()));
        this.inbox = new SimpleListProperty<>();
        this.inbox.set(inboxContent);
        this.emailAddress = new SimpleStringProperty(client.getUser());
    }

    public static Model getInstance(){ return new Model(); }
    public Client getClient() { return client; }
    public ListProperty<Email> getInboxProperty() { return inbox; }
    public ObservableList<Email> getInboxContent() {
        return inboxContent;
    }
    public StringProperty getEmailAddressProperty() { return emailAddress; }

    public void addEmails(List<Email> emails){
        inboxContent.addAll(emails);
        inboxContent.sort(null);
    }

    public void deleteEmail(Email email) { inboxContent.remove(email); }
}
