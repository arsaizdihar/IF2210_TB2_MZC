package mzc.app.adapter.file;

import lombok.Getter;
import lombok.Setter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistoryBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileFixedBillAdapter extends FileModelAdapter<FixedBill> implements IFixedBillAdapter {
    @Setter
    private @NotNull ICustomerAdapter customerAdapter;

    @Getter
    private final @NotNull FileProductHistoryBillAdapter productBillAdapter;

    protected FileFixedBillAdapter(@NotNull IFileDataLoader loader, @NotNull FileProductHistoryAdapter productAdapter) {
        super(loader);
        this.productBillAdapter = new FileProductHistoryBillAdapter(loader, productAdapter);
    }
    @Override
    protected Class<FixedBill> getType() {
        return FixedBill.class;
    }

    @Override
    public @NotNull List<FixedBill> getByCustomerId(long customerId) {
        return loadAllCustomers(getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId)).collect(Collectors.toList()));
    }

    @Override
    public @NotNull List<ProductHistoryBill> getProducts(FixedBill bill) {
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
