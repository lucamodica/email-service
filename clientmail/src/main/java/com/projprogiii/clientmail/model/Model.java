package com.projprogiii.clientmail.model;

import com.projprogiii.clientmail.model.components.Client;
import com.projprogiii.clientmail.model.components.ConfigManager;
import com.projprogiii.lib.objects.Email;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Classe Client, conterrà la lista di mail che sarà il model
 */
public class Model {
    private final ListProperty<Email> inbox;
    private final ObservableList<Email> inboxContent;
    private final StringProperty emailAddress;
    private final Client client;
    private final ConfigManager configManager;

    /**
     * Costruttore della classe.
     *
     * @param emailAddress   indirizzo email
     *
     */
    public Model(String emailAddress) {
        this.inboxContent = FXCollections.observableList(new LinkedList<>());
        this.inbox = new SimpleListProperty<>();
        this.inbox.set(inboxContent);
        this.emailAddress = new SimpleStringProperty(emailAddress);

        //TODO: test only, to be deleted
        client = null;
        configManager = null;
    }

    /**
     * @return      lista di email
     *
     */
    public ListProperty<Email> inboxProperty() {
        return inbox;
    }

    /**
     *
     * @return   indirizzo email della casella postale
     *
     */
    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    
    public void deleteEmail(Email email) {
        inboxContent.remove(email);
    }

    /**
     *genera email random da aggiungere alla lista di email, ese verranno mostrate nella ui
     */
    public void generateRandomEmails(int n) {
        String[] people = new String[] {"Paolo", "Alessandro", "Enrico", "Giulia", "Gaia", "Simone"};
        String[] subjects = new String[] {
                "Importante", "A proposito della nostra ultima conversazione", "Tanto va la gatta al lardo",
                "Non dimenticare...", "Domani scuola" };
        String[] texts = new String[] {
                "È necessario che ci parliamo di persona, per mail rischiamo sempre fraintendimenti",
                "Ricordati di comprare il latte tornando a casa",
                "L'appuntamento è per domani alle 9, ci vediamo al solito posto",
                "Ho sempre pensato valesse 42, tu sai di cosa parlo"
        };
        Random r = new Random();
        for (int i=0; i<n; i++) {
            Email email = new Email(
                    people[r.nextInt(people.length)],
                    List.of(people[r.nextInt(people.length)]),
                    subjects[r.nextInt(subjects.length)],
                    texts[r.nextInt(texts.length)],
                    false);
            inboxContent.add(email);
        }

    }
}
