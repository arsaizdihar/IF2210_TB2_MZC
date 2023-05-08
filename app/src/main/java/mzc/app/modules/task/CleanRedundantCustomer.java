package mzc.app.modules.task;

import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.CustomerType;
import org.jetbrains.annotations.NotNull;

public class CleanRedundantCustomer implements Runnable {
    private final @NotNull IMainAdapter adapter;

    public CleanRedundantCustomer(@NotNull IMainAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void run() {
        var toDelete = adapter.getCustomer().getAll().stream().filter(customer -> {
            if (customer.getType() != CustomerType.BASIC) return false;
            return adapter.getFixedBill().getByCustomerId(customer.getId()).size() == 0;
        }).toList();
        toDelete.forEach(customer -> {
            adapter.getBill().getByCustomerId(customer.getId()).forEach(bill -> {
                adapter.getBill().delete(bill);
            });

            adapter.getCustomer().delete(customer);
        });
    }
}
