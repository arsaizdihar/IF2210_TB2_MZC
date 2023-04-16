package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class CustomerAdapter extends ModelAdapter<Customer> implements ICustomerAdapter {
    private final @NonNull IBillAdapter billAdapter;

    CustomerAdapter(@NonNull IBillAdapter billAdapter) {
        this.billAdapter = billAdapter;
    }

    @Override
    public Customer getById(long id) {
        return super.getById(id);
    }

    @Override
    protected @NonNull Class<Customer> getType() {
        return Customer.class;
    }

    @Override
    public List<Bill> getBills(@NonNull Customer customer) {
        if (customer.isBillsLoaded()) return customer.getBills();
        customer.setBillsLoaded(true);
        List<Bill> result = billAdapter.getByCustomerId(customer.getId());

        System.out.println("\n\nRESULT");
        System.out.println(result);
        customer.setBills(result);
        return result;
    }

    @Override
    public List<Customer> getRegisteredCustomer() {
        return getData().values().stream().filter(c -> c.getType() != CustomerType.BASIC).collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(getData().values());
    }
}
