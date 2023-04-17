package mzc.app.adapter.base;

import mzc.app.model.Bill;
import mzc.app.model.Customer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ICustomerAdapter extends IBasicAdapter<Customer> {
    List<Bill> getBills(@NotNull Customer customer);

    List<Customer> getRegisteredCustomer();
}
