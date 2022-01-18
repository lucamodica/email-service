package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.ServerResponseName;

import java.io.Serializable;

public record ServerResponse(ServerResponseName responseName,
                             Object[] args) implements Serializable {
}
