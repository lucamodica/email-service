module com.example.lightningmail {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.example.lightningmail to javafx.fxml;
    exports com.example.lightningmail;
}