package mzc.app.adapter.file;

import lombok.Getter;
import lombok.Setter;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class FileBillAdapter extends FileModelAdapter<Bill> implements IBillAdapter {
    @Setter
    private @NotNull FileCustomerAdapter customerAdapter;

    @Getter
    private final @NotNull FileProductBillAdapter productBillAdapter;

    protected FileBillAdapter(@NotNull IFileDataLoader loader, @NotNull FileProductBillAdapter productBillAdapter) {
        super(loader);
        this.productBillAdapter = productBillAdapter;
    }

    @Override
    public Bill getById(long id) {
        Bill res = super.getById(id);
        if (res != null) {
            res.setCustomer(customerAdapter.getById(res.getCustomerId()));
        }
        return res;
    }

    @Override
    public @NotNull Set<Bill> getByCustomerId(long customerId) {
        return loadAllCustomers(getClones(getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId))));
    }

    @Override
    public @NotNull Set<Bill> getAll() {
        return loadAllCustomers(super.getAll());
    }

    @Override
    public @NotNull Set<ProductBill> getProducts(Bill bill) {
        if (bill.isProductsLoaded()) return bill.getProducts();
        Set<ProductBill> result = getProductBillAdapter().getByBillId(bill.getId());
        bill.setProductsLoaded(true);
        bill.setProducts(result);
        return result;
    }

    @Override
    protected @NotNull Class<Bill> getType() {
        return Bill.class;
    }

    public Set<Bill> loadAllCustomers(Set<Bill> bills) {
        bills.forEach(b -> b.setCustomer(customerAdapter.getById(b.getCustomerId())));
        return bills;
    }
}
