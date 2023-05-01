package mzc.app.bootstrap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import mzc.app.view.MainView;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

public class GUIApp extends Application {
    @SneakyThrows
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView.getView(), 1280, 720);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/mzc/app/css/colors.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/mzc/app/css/main-styles.css")).toExternalForm());
        stage.setTitle("MZC");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void launchGUI() {
        launch();
    }
}
