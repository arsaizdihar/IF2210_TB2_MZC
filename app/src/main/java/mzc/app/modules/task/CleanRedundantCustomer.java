package mzc.app.modules.task;

import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CleanRedundantCustomer implements Runnable {
    private final @NotNull IMainAdapter adapter;

    public CleanRedundantCustomer(@NotNull IMainAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void run() {
        var customers = adapter.getCustomer().getAll().stream().filter(customer -> customer.getType() == CustomerType.BASIC);

        List<Customer> toDelete = new ArrayList<>();

        customers.forEach(customer -> {
            if (adapter.getFixedBill().getByCustomerId(customer.getId()).size() == 0) {
                toDelete.add(customer);
            }
        });

        toDelete.forEach(customer -> {
            adapter.getBill().getByCustomerId(customer.getId()).forEach(bill -> {
                adapter.getBill().delete(bill);
            });

            adapter.getCustomer().delete(customer);
        });
    }
}
