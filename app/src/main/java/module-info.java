module mzc.app {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires com.google.gson;
    requires org.apache.commons.io;

    opens mzc.app.view_model to javafx.fxml;
    opens mzc.app.model to org.hibernate.orm.core, com.google.gson;
    exports mzc.app;
}