package com.projprogiii.lib.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mail implements Serializable {
    private String sender;
    private List<String> receivers;
    private String subject;
    private String text;

    private Mail() {}

    /**
     * Costruttore della classe.
     *
     * @param sender     email del mittente
     * @param receivers  emails dei destinatari
     * @param subject    oggetto della mail
     * @param text       testo della mail
     */


    public Mail(String sender, List<String> receivers, String subject, String text) {
        this.sender = sender;
        this.subject = subject;
        this.text = text;
        this.receivers = new ArrayList<>(receivers);
    }

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    /**
     * @return      stringa composta dagli indirizzi e-mail del mittente più destinatari
     */
    @Override
    public String toString() {
        return String.join(" - ", List.of(this.sender,this.subject));
    }
}

