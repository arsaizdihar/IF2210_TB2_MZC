package mzc.app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;

public class ViewLoader {
    public static Parent load(String filename, Callback<Class<?>, Object> viewModelFactory) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewLoader.class.getResource("/mzc/app/" + filename));
        loader.setControllerFactory(viewModelFactory);
        return loader.load();
    }
}
