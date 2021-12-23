module com.example.clientmail {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.clientmail to javafx.fxml;
    exports com.example.clientmail;
}