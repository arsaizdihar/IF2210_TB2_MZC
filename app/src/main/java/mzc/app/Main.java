package mzc.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import mzc.app.utils.ViewLoader;
import mzc.app.utils.ViewModelFactory;
import mzc.app.view.MainView;

public class Main extends Application {
    @SneakyThrows
    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView.getView(), 320, 240);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setTitle("MZC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}