package mzc.app.adapter.file;

import lombok.NonNull;
import lombok.Setter;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class FileBillAdapter <T extends IFileDataLoader<Bill>> extends FileModelAdapter<Bill, T> implements IBillAdapter {
    @Setter
    private @NonNull ICustomerAdapter customerAdapter;

    abstract protected @NonNull FileProductBillAdapter<? extends IFileDataLoader<ProductBill>> getProductBillAdapter();

    @Override
    public Bill getById(long id) {
        Bill res = super.getById(id);
        if (res != null) {
            res.setCustomer(customerAdapter.getById(res.getId()));
        }
        return res;
    }

    @Override
    public @NonNull List<Bill> getByCustomerId(long customerId) {
        return loadAllCustomers(getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId)).collect(Collectors.toList()));
    }

    @Override
    public @NonNull List<Bill> getAll() {
        return loadAllCustomers(super.getAll());
    }

    @Override
    public @NonNull List<ProductBill> getProducts(Bill bill) {
        if (bill.isProductsLoaded()) return bill.getProducts();
        List<ProductBill> result = getProductBillAdapter().getByBillId(bill.getId());
        bill.setProductsLoaded(true);
        bill.setProducts(result);
        return result;
    }

    @Override
    protected @NonNull Class<Bill> getType() {
        return Bill.class;
    }

    public List<Bill> loadAllCustomers(List<Bill> bills) {
        bills.forEach(b -> b.setCustomer(customerAdapter.getById(b.getCustomerId())));
        return bills;
    }
}
