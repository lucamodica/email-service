package com.projprogiii.lib.objects;

import com.projprogiii.lib.utilities.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Email implements Serializable, Comparable<Email>{

    private final String sender;
    private final List<String> receivers;
    private final String subject;
    private final String text;
    private boolean isToRead;
    private Date date;


    public Email(String sender, List<String> receivers,
                 String subject, String text){
        this.sender = sender;
        this.subject = subject;
        this.text = text;
        this.receivers = new ArrayList<>(receivers);
        this.date = new Date();
        this.isToRead = false;
    }

    public Email(String sender, List<String> receivers, String subject,
                 String text, Date date) {
        this(sender, receivers, subject, text);
        this.date = date;
        this.isToRead = false;
    }

    public Email(String sender, List<String> receivers, String subject,
                 String text, Date date, boolean isToRead) {
        this(sender, receivers, subject, text, date);
        this.isToRead = isToRead;
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

    public Date getDate() { return date; }
    public boolean getIsToRead() {
        return isToRead;
    }
    public String dateToString(){
        if (this.date == null) return "";
        return Util.formatDate(this.date);
    }

    public void setToRead(boolean b){
        this.isToRead = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(sender, email.sender) && Objects.equals(receivers, email.receivers)
                && Objects.equals(subject, email.subject) && Objects.equals(text, email.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receivers, subject, text);
    }

    /**
     * @return sender - subject
     */
    @Override
    public String toString() {
        return String.join(" - ", List.of(this.sender, this.subject));
    }

    @Override
    public int compareTo(Email email) {
        return email.getDate().compareTo(this.date);
    }
}

