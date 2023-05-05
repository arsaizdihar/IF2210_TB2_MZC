package mzc.app.adapter.file;

import lombok.Getter;
import lombok.Setter;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FileFixedBillAdapter extends FileModelAdapter<FixedBill> implements IFixedBillAdapter {
    @Setter
    private @NotNull FileCustomerAdapter customerAdapter;

    @Getter
    private final @NotNull FileProductHistoryAdapter productHistoryAdapter;

    protected FileFixedBillAdapter(@NotNull IFileDataLoader loader, @NotNull FileProductHistoryAdapter productAdapter) {
        super(loader);
        this.productHistoryAdapter = productAdapter;
    }
    @Override
    protected Class<FixedBill> getType() {
        return FixedBill.class;
    }

    @Override
    public @NotNull Set<FixedBill> getByCustomerId(long customerId) {
        return loadAllCustomers(getClones(getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId))));
    }

    @Override
    public @NotNull Set<ProductHistory> getProducts(FixedBill bill) {
        if (bill.isProductsLoaded()) return bill.getProducts();
        Set<ProductHistory> result = getProductHistoryAdapter().getByBillId(bill.getId());
        bill.setProductsLoaded(true);
        bill.setProducts(result);
        return result;
    }

    public Set<FixedBill> loadAllCustomers(Set<FixedBill> bills) {
        bills.forEach(b -> b.setCustomer(customerAdapter.getById(b.getCustomerId())));
        return bills;
    }
}
