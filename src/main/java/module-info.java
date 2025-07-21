module com.example.locationvoituresapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.autorental.runtime to javafx.fxml;
    opens com.autorental.controllers to javafx.fxml;
    opens com.autorental.model to javafx.base, org.hibernate.orm.core;
    exports com.autorental.runtime;
    opens com.autorental.utils to org.hibernate.orm.core;
}