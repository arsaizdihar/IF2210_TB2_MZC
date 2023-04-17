package mzc.app.adapter.file;

import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FileCustomerAdapter<T extends IFileDataLoader<Customer>> extends FileModelAdapter<Customer, T> implements ICustomerAdapter {
    private final @NotNull IBillAdapter billAdapter;

    public FileCustomerAdapter(@NotNull IBillAdapter billAdapter) {
        this.billAdapter = billAdapter;
    }

    @Override
    public List<Bill> getBills(@NotNull Customer customer) {
        if (customer.isBillsLoaded()) return customer.getBills();
        customer.setBillsLoaded(true);
        List<Bill> result = billAdapter.getByCustomerId(customer.getId());
        customer.setBills(result);
        return result;
    }

    @Override
    public List<Customer> getRegisteredCustomer() {
        return getData().values().stream().filter(c -> c.getType() != CustomerType.BASIC).collect(Collectors.toList());
    }
}
