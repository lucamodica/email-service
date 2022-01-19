package com.projprogiii.lib.objects;

import com.projprogiii.lib.utils.CommonUtil;

import java.io.Serializable;
import java.util.*;

public class Email implements Serializable, Comparable<Email>{

    private int id;
    private final String sender;
    private final List<String> receivers;
    private final String subject;
    private final String text;
    private Date date;
    private boolean isToRead;

    public Email(String sender, List<String> receivers,
                 String subject, String text){
        this.sender = sender;
        this.subject = subject;
        this.text = text;
        this.receivers = new ArrayList<>(receivers);
        this.date = new Date();
        this.isToRead = false;
        this.id = this.hashCode();
    }

    public Email (Email email, boolean isToRead){
        this.id = email.id;
        this.sender = email.sender;
        this.receivers = email.receivers;
        this.subject = email.subject;
        this.text = email.text;
        this.date = email.date;
        this.isToRead = isToRead;
    }
    public static Email generateEmptyEmail(){
        Email email = new Email("", List.of(""), "",
                "");
        email.date = null;
        email.id = email.hashCode();

        return email;
    }

    public int getId() {
        return id;
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

    public void setToRead(boolean b){
        this.isToRead = b;
    }
    public static Email setToRead(Email email){
        return new Email(email, true);
    }
    public boolean isToRead() {
        return isToRead;
    }

    public static boolean isEmpty(Email email){
        return email.equals(generateEmptyEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return getId() == email.getId();
    }
    @Override
    public int hashCode() {
        return Objects.hash(sender, receivers, subject, text, date);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receivers=" + receivers +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", isToRead=" + isToRead +
                ", date=" + date +
                '}';
    }

    public String dateToString(){
        if (this.date == null) return "";
        return CommonUtil.formatDate(this.date);
    }

    @Override
    public int compareTo(Email email) {
        return email.getDate().compareTo(this.date);
    }
}

