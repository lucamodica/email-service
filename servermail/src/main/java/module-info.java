module servermail {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;

    requires mailib;
        
    opens com.projprogiii.servermail to javafx.fxml;
    exports com.projprogiii.servermail;
    exports com.projprogiii.servermail.model;
    opens com.projprogiii.servermail.model to javafx.fxml;
    exports com.projprogiii.servermail.controller;
    opens com.projprogiii.servermail.controller to javafx.fxml;
}