module mzc.app {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
            
    opens mzc.app.view_model to javafx.fxml;
    exports mzc.app;
}