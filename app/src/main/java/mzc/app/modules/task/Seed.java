package mzc.app.modules.task;

import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import mzc.app.model.Product;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Seed implements Runnable {

    private final @NotNull IMainAdapter adapter;

    public Seed(@NotNull IMainAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void run() {
        var productAdapter = adapter.getProduct();
        Product.getSeed().forEach(productAdapter::persist);
        var customerAdapter = adapter.getCustomer();
        Customer.getSeeder().forEach(customerAdapter::persist);

        Random random = new Random();

        var products = new ArrayList<>(adapter.getProduct().getAll().stream().toList());
        var customers = new ArrayList<>(adapter.getCustomer().getAll().stream().toList());

        for (int i = 0; i < 10; i++) {
            var date = LocalDate.now().minusDays(i);

            for (int j = 0; j < 3; j++) {
                var customer = new Customer();
                adapter.getCustomer().persist(customer);

                createProductHistory(random, products, date, customer);
            }

            int memberSize = random.nextInt(customers.size());
            Collections.shuffle(customers, random);

            for (int j = 0; j < memberSize; j++) {
                var customer = customers.get(j);

                createProductHistory(random, products, date, customer);
            }
        }
    }

    private void createProductHistory(Random random, ArrayList<Product> products, LocalDate date, Customer customer) {
        var bill = new FixedBill(customer, Timestamp.valueOf(date.atStartOfDay()));
        adapter.getFixedBill().persist(bill);
        Collections.shuffle(products, random);
        int size = random.nextInt(products.size());

        for (int k = 0; k < size; k++) {
            var productHistory = new ProductHistory(products.get(0), bill, random.nextInt(3));
            adapter.getProductHistory().persist(productHistory);
        }
    }
}
