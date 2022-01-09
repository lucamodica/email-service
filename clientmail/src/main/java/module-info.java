module clientmail {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
            
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires com.dlsc.formsfx;

    requires mailib;

    opens com.projprogiii.clientmail to javafx.fxml;
    exports com.projprogiii.clientmail;
    exports com.projprogiii.clientmail.controller;
    opens com.projprogiii.clientmail.controller to javafx.fxml;
}