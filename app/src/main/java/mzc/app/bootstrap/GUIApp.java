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
//        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;0,600;0,700;0,800;1,400;1,500;1,600;1,700;1,800&display=swap");
        stage.setTitle("MZC");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
