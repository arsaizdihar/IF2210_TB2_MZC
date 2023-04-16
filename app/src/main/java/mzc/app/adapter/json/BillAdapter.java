package mzc.app.adapter.json;

import lombok.NonNull;
import lombok.Setter;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {
    private final @NonNull ProductBillAdapter productBillAdapter;
    @Setter
    private @NonNull ICustomerAdapter customerAdapter;

    BillAdapter(@NonNull ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    public Bill getById(long id) {
        var res = super.getById(id);
        if (res != null) {
            res.setCustomer(customerAdapter.getById(res.getId()));
        }
        return res;
    }

    @Override
    public @NonNull List<Bill> getByCustomerId(@NonNull Long customerId) {
        return loadAllCustomers(getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId)).toList());
    }

    @Override
    public @NonNull List<Bill> getAll() {
        return loadAllCustomers(new ArrayList<>(getData().values()));
    }

    @Override
    public @NonNull List<ProductBill> getProducts(Bill bill) {
        if (bill.isProductsLoaded()) return bill.getProducts();
        var result = productBillAdapter.getByBillId(bill.getId());
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
