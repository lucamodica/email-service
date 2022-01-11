package com.projprogiii.clientmail.model;

import com.projprogiii.clientmail.model.components.Client;
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

    //TODO: test array only, to be deleted
    static String[] people = new String[] {"Paolo", "Alessandro", "Enrico", "Giulia", "Gaia", "Simone"};
    static String[] subjects = new String[] {
            "Importante", "A proposito della nostra ultima conversazione", "Tanto va la gatta al lardo",
            "Non dimenticare...", "Domani scuola" };
    static String[] texts = new String[] {
            "È necessario che ci parliamo di persona, per mail rischiamo sempre fraintendimenti",
            "Ricordati di comprare il latte tornando a casa",
            "L'appuntamento è per domani alle 9, ci vediamo al solito posto",
            "Ho sempre pensato valesse 42, tu sai di cosa parlo"
    };


    /**
     * Class constructor and getInstance function.
     */
    private Model() {
        client = Client.getInstance("luca.modica@unito.it");

        this.inboxContent = FXCollections.observableList(new ArrayList<>());
        this.inbox = new SimpleListProperty<>();
        this.inbox.set(inboxContent);

        this.emailAddress = new SimpleStringProperty(client.getUser().emailAddress());
    }
    public static Model getInstance(){
        return new Model();
    }

    public Client getClient() {
        return client;
    }

    /**
     * @return lista di emailAddress
     *
     */
    public ListProperty<Email> inboxProperty() {
        return inbox;
    }

    /**
     *
     * @return indirizzo emailAddress della casella postale
     *
     */
    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    
    public void deleteEmail(Email email) {
        inboxContent.remove(email);
    }

    public void addRandomEmail() {
        inboxContent.add(generateRandomEmail());
        inboxContent.sort(null);
    }

    /**
     *genera emailAddress random da aggiungere alla lista di emailAddress, ese verranno mostrate nella ui
     */
    public void generateRandomEmails(int n) {

        Random r = new Random();
        for (int i=0; i<n; i++) {
            Email email = new Email(
                    people[r.nextInt(people.length)],
                    List.of(people[r.nextInt(people.length)]),
                    subjects[r.nextInt(subjects.length)],
                    texts[r.nextInt(texts.length)],
                    new Date());
            inboxContent.add(email);
        }
        inboxContent.sort(null);
    }

    public Email generateRandomEmail(){
        Random r = new Random();
        return new Email(
                people[r.nextInt(people.length)],
                List.of(people[r.nextInt(people.length)]),
                subjects[r.nextInt(subjects.length)],
                texts[r.nextInt(texts.length)],
                new Date(), true);
    }
}
