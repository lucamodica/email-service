package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.ServerResponseName;

import java.io.Serializable;
import java.util.List;

public record ServerResponse(ServerResponseName responseName,
                             List<Email> args) implements Serializable {
    @Override
    public String toString() {
        return "ServerResponse{" +
                "responseName=" + responseName +
                ", args=" + args +
                '}';
    }
}
