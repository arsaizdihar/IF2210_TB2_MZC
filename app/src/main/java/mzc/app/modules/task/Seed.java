package mzc.app.modules.task;

import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Customer;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

public class Seed implements Runnable {

    private final @NotNull IMainAdapter adapter;
    private final @NotNull String imagePath;

    public Seed(@NotNull IMainAdapter adapter, @NotNull String imagePath) {
        this.adapter = adapter;
        this.imagePath = imagePath;
    }

    @Override
    public void run() {
        var productAdapter = adapter.getProduct();
        Product.getSeed(imagePath).forEach(productAdapter::persist);
        var customerAdapter = adapter.getCustomer();
        Customer.getSeeder().forEach(customerAdapter::persist);
    }
}
