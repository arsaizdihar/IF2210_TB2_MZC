package mzc.app.adapter.file;

import lombok.NonNull;
import lombok.Setter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductBill;
import mzc.app.model.ProductHistoryBill;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class FileFixedBillAdapter <T extends IFileDataLoader<FixedBill>> extends FileModelAdapter<FixedBill, T> implements IFixedBillAdapter {
    @Setter
    private @NonNull ICustomerAdapter customerAdapter;

    abstract protected @NonNull FileProductHistoryBillAdapter<? extends IFileDataLoader<ProductHistoryBill>> getProductBillAdapter();
    @Override
    protected Class<FixedBill> getType() {
        return FixedBill.class;
    }

    @Override
    public @NonNull List<FixedBill> getByCustomerId(long customerId) {
        return loadAllCustomers(getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId)).collect(Collectors.toList()));
    }

    @Override
    public @NonNull List<ProductHistoryBill> getProducts(FixedBill bill) {
        if (bill.isProductsLoaded()) return bill.getProducts();
        List<ProductHistoryBill> result = getProductBillAdapter().getByBillId(bill.getId());
        bill.setProductsLoaded(true);
        bill.setProducts(result);
        return result;
    }

    public List<FixedBill> loadAllCustomers(List<FixedBill> bills) {
        bills.forEach(b -> b.setCustomer(customerAdapter.getById(b.getCustomerId())));
        return bills;
    }
}
