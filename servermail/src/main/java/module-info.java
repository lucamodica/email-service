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
    exports com.projprogiii.servermail.server;
    opens com.projprogiii.servermail.server to javafx.fxml;
    exports com.projprogiii.servermail.model.db;
    opens com.projprogiii.servermail.model.db to javafx.fxml;
    exports com.projprogiii.servermail.model.sync;
    opens com.projprogiii.servermail.model.sync to javafx.fxml;
    exports com.projprogiii.servermail.model.log;
    opens com.projprogiii.servermail.model.log to javafx.fxml;
}