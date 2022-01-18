package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.CommandName;

import java.io.Serializable;

public record ClientRequest(String auth, CommandName cmdName,
                            Email email) implements Serializable {

}
