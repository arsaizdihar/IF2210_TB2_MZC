module mzc.app {
    uses mzc.app.modules.plugins.Plugin;
    requires javafx.base;
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
    requires com.fasterxml.jackson.dataformat.xml;
    requires org.jetbrains.annotations;

    opens mzc.app.view_model to javafx.fxml;
    opens mzc.app.bootstrap to javafx.graphics;
    opens mzc.app.model to org.hibernate.orm.core, com.google.gson, com.fasterxml.jackson.databind;
    exports mzc.app;
}