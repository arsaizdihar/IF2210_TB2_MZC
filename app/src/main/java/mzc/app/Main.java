package mzc.app;

import javafx.application.Application;
import mzc.app.adapter.Datastore;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.bootstrap.GUIApp;
import mzc.app.model.Customer;
import mzc.app.model.Product;

public class Main {
    public static void main(String[] args) {
        App app = AppManager.get();
        app.init();
        app.postInit();

        for (var arg : args) {
            if (arg.startsWith("--seed")) {
                var adapter = Datastore.getManager();

                var split = arg.split("=");

                if (split.length < 2) {
                    throw new RuntimeException("Invalid command. Format: --seed=\"filepath\"");
                }

                var productAdapter = adapter.getAdapter().getProduct();
                Product.getSeed(split[1]).forEach(productAdapter::persist);
                var customerAdapter = adapter.getAdapter().getCustomer();
                Customer.getSeeder().forEach(customerAdapter::persist);
            }
        }

        Application.launch(GUIApp.class);
    }
}