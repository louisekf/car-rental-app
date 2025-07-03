module com.example.locationvoituresapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.locationvoituresapp to javafx.fxml;
    exports com.example.locationvoituresapp;
}