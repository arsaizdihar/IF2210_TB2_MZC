package mzc.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import mzc.app.modules.setting.AppSettingManager;
import mzc.app.view.MainView;
import org.kordamp.bootstrapfx.BootstrapFX;


public class Main extends Application {
    @SneakyThrows
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView.getView(), 1280, 720);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("MZC");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void boostrap() {
        // initialize config
        AppSettingManager.get();
    }

    public static void main(String[] args) {
        launch();
    }
}