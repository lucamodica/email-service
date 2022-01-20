module clientmail {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
            
    requires org.kordamp.bootstrapfx.core;

    requires com.dlsc.formsfx;

    requires mailib;

    opens com.projprogiii.clientmail to javafx.fxml;
    exports com.projprogiii.clientmail;
    exports com.projprogiii.clientmail.controller;
    opens com.projprogiii.clientmail.controller to javafx.fxml;
    exports com.projprogiii.clientmail.model;
    opens com.projprogiii.clientmail.model to javafx.fxml;
    exports com.projprogiii.clientmail.model.client;
    opens com.projprogiii.clientmail.model.client to javafx.fxml;
    exports com.projprogiii.clientmail.model.client.config;
    opens com.projprogiii.clientmail.model.client.config to javafx.fxml;
    exports com.projprogiii.clientmail.scene;
    opens com.projprogiii.clientmail.scene to javafx.fxml;
}