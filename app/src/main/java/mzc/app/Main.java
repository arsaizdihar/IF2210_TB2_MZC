package mzc.app;

import javafx.application.Application;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.bootstrap.GUIApp;

public class Main {
    public static void main(String[] args) {
        App app = AppManager.get();
        app.init();
        app.postInit();

        Application.launch(GUIApp.class);
    }
}