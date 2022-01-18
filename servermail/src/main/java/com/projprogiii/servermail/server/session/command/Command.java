package com.projprogiii.servermail.server.session.command;

import com.projprogiii.lib.objects.DataPackage;

import java.io.IOException;

public abstract class Command {
    public abstract void init(DataPackage pkg) throws IOException;
}
