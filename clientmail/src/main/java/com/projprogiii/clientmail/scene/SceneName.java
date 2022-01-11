package com.projprogiii.clientmail.scene;

import java.util.Locale;

public enum SceneName {
    MAIN,
    COMPOSE;

    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ROOT);
    }
}
