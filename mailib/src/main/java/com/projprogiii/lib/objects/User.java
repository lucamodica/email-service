package com.projprogiii.lib.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public record User(int id, String emailAddress) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(emailAddress, user.emailAddress);
    }

    public static int genUserId(String emailAddress){
        return Objects.hash(emailAddress, new Date());
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
