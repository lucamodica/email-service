package com.projprogiii.lib.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Email(String sender, List<String> receivers, String subject,
                    String text, boolean isToRead) implements Serializable {

    public Email(String sender, List<String> receivers, String subject,
                 String text, boolean isToRead) {
        this.sender = sender;
        this.subject = subject;
        this.text = text;
        this.receivers = new ArrayList<>(receivers);
        this.isToRead = isToRead;
    }

    public Email setToRead(boolean b){
        return new Email(sender, receivers, subject, text, b);
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
}

