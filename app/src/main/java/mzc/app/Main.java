package mzc.app;

import javafx.application.Application;
import mzc.app.adapter.AdapterManager;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.bootstrap.GUIApp;
import mzc.app.model.Product;

public class Main {
    public static void main(String[] args) {
        App app = AppManager.get();
        app.init();
        app.postInit();

        if (args.length == 2 || args.length == 3) {
            if (!args[0].equals("seed")) {
                throw new RuntimeException("Seeder usage: java ...jar seed absolute-image-path [force]");
            }

            var adapter = new AdapterManager();

            if (adapter.getAdapter().getProduct().getAll().size() == 0 || args.length == 3) {
                var productAdapter = adapter.getAdapter().getProduct();
                Product.getSeed(args[1]).forEach(productAdapter::persist);
            }
        }

        Application.launch(GUIApp.class);
    }
}