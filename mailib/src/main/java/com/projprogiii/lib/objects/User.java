package com.projprogiii.lib.objects;

import java.io.Serializable;
import java.util.Objects;


public record User(String emailAddress) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(emailAddress, user.emailAddress());
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
