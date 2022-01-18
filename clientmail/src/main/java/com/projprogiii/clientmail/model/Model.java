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

/**
 * Model class
 */
public class Model {

    private final Client client;

    private final ListProperty<Email> inbox;
    private final ObservableList<Email> inboxContent;
    private final StringProperty emailAddress;

    /**
     * Class constructor and getInstance function.
     */
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

    /**
     * @return lista di emailAddress
     *
     */
    public ListProperty<Email> inboxProperty() { return inbox; }

    /**
     *
     * @return indirizzo emailAddress della casella postale
     *
     */
    public StringProperty emailAddressProperty() { return emailAddress; }

    public void addEmail(Email email){
        inboxContent.add(email);
        inboxContent.sort(null);
    }

    public void deleteEmail(Email email) { inboxContent.remove(email); }
}
