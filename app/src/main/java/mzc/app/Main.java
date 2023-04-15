package mzc.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import mzc.app.adapter.AdapterManager;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.utils.ViewLoader;
import mzc.app.utils.ViewModelFactory;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.util.Map;

public class Main extends Application {
    @SneakyThrows
    @Override
    public void start(Stage stage) {
        Parent parent = ViewLoader.load("main-view.fxml", ViewModelFactory::injectViewModel);
//        Map<String, Class<? extends IMainAdapter>> adapters = AdapterManager.getAvailableAdapters();
//        for (var adapter: adapters.keySet()) {
//            System.out.println(adapter);
//        }
        Scene scene = new Scene(parent, 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("MZC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}