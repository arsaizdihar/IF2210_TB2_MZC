package mzc.app.adapter.base;

import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface ICustomerAdapter extends IBasicAdapter<Customer> {
    @NotNull Set<Bill> getBills(@NotNull Customer customer);

    @NotNull Set<FixedBill> getFixedBills(@NotNull Customer customer);

    @NotNull Set<Customer> getRegisteredCustomer();
}
