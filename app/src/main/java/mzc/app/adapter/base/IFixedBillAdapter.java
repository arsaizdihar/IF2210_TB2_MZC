package mzc.app.adapter.base;

import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface IFixedBillAdapter extends IBasicAdapter<FixedBill> {
    @NotNull Set<FixedBill> getByCustomerId(long customerId);

    @NotNull Set<ProductHistory> getProducts(FixedBill bill);
}
