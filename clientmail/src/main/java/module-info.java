module clientmail {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
    requires mailib;

    opens com.projprogiii.clientmail to javafx.fxml;
    exports com.projprogiii.clientmail;
}