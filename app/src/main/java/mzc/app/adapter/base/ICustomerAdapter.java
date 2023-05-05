package mzc.app.adapter.base;

import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICustomerAdapter extends IBasicAdapter<Customer> {
    @NotNull List<Bill> getBills(@NotNull Customer customer);

    @NotNull List<FixedBill> getFixedBills(@NotNull Customer customer);

    List<Customer> getRegisteredCustomer();
}
