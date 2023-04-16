package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.Bill;
import mzc.app.model.Customer;

import java.util.List;

public interface ICustomerAdapter extends IBasicAdapter<Customer> {
    List<Bill> getBills(@NonNull Customer customer);

    List<Customer> getRegisteredCustomer();
}
