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
    public @NotNull List<Bill> getBills(@NotNull Customer customer) {
        if (customer.isBillsLoaded()) return customer.getBills();
        customer.setBillsLoaded(true);
        List<Bill> result = billAdapter.getByCustomerId(customer.getId());
        customer.setBills(result);
        return result;
    }

    @Override
    public @NotNull List<FixedBill> getFixedBills(@NotNull Customer customer) {
        if (customer.isFixedBillsLoaded()) return customer.getFixedBills();
        customer.setFixedBillsLoaded(true);
        List<FixedBill> result = fixedBillAdapter.getByCustomerId(customer.getId());
        customer.setFixedBills(result);
        return result;
    }

    @Override
    public List<Customer> getRegisteredCustomer() {
        return getClones(getData().values().stream().filter(c -> c.getType() != CustomerType.BASIC).collect(Collectors.toList()));
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
