package mzc.app.adapter.file;

import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.model.FixedBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileCustomerAdapter extends FileModelAdapter<Customer> implements ICustomerAdapter {
    private final @NotNull IBillAdapter billAdapter;
    private final @NotNull IFixedBillAdapter fixedBillAdapter;


    public FileCustomerAdapter(@NotNull IFileDataLoader loader, @NotNull IBillAdapter billAdapter, @NotNull IFixedBillAdapter fixedBillAdapter) {
        super(loader);
        this.billAdapter = billAdapter;
        this.fixedBillAdapter = fixedBillAdapter;
    }

    @Override
    public @NotNull Set<Bill> getBills(@NotNull Customer customer) {
        if (customer.isBillsLoaded()) return customer.getBills();
        customer.setBillsLoaded(true);
        Set<Bill> result = billAdapter.getByCustomerId(customer.getId());
        customer.setBills(result);
        return result;
    }

    @Override
    public @NotNull Set<FixedBill> getFixedBills(@NotNull Customer customer) {
        if (customer.isFixedBillsLoaded()) return customer.getFixedBills();
        customer.setFixedBillsLoaded(true);
        Set<FixedBill> result = fixedBillAdapter.getByCustomerId(customer.getId());
        customer.setFixedBills(result);
        return result;
    }

    @Override
    public @NotNull Set<Customer> getRegisteredCustomer() {
        return getClones(getData().values().stream().filter(c -> c.getType() != CustomerType.BASIC));
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
