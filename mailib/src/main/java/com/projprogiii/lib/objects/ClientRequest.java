package com.projprogiii.lib.objects;

import com.projprogiii.lib.enums.CommandName;

import java.io.Serializable;
import java.util.List;

public record ClientRequest(String auth, CommandName cmdName,
                            List<Object> args) implements Serializable {
}
