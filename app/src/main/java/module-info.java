open module mzc.app {
    uses mzc.app.modules.plugins.Plugin;
    requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    requires transitive org.controlsfx.controls;
    requires transitive com.dlsc.formsfx;
    requires transitive org.kordamp.bootstrapfx.core;
    requires transitive jakarta.persistence;
    requires transitive lombok;
    requires transitive org.hibernate.orm.core;
    requires transitive java.naming;
    requires transitive com.google.gson;
    requires transitive org.apache.commons.io;
    requires transitive com.fasterxml.jackson.dataformat.xml;
    requires transitive org.jetbrains.annotations;
    requires transitive sql.formatter;
    requires transitive com.zaxxer.hikari;

//    opens mzc.app.view_model to javafx.fxml;
//    opens mzc.app.bootstrap to javafx.graphics;
//    opens mzc.app.model to org.hibernate.orm.core, com.google.gson, com.fasterxml.jackson.databind;

    exports mzc.app;
    exports mzc.app.adapter.base;
    exports mzc.app.modules.setting;
    exports mzc.app.modules.pricing;
    exports mzc.app.modules.pricing.pipelines;
    exports mzc.app.modules.pricing.price;
    exports mzc.app.modules.plugins;
    exports mzc.app.bootstrap;
    exports mzc.app.annotation;
    exports mzc.app.model;
    exports mzc.app.adapter.obj;
    exports mzc.app.adapter.file;
    exports mzc.app.adapter.xml;
    exports mzc.app.adapter.json;
    exports mzc.app.adapter.orm;
}