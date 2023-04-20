package mzc.app.adapter.base;

import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IFixedBillAdapter extends IBasicAdapter<FixedBill> {
    @NotNull List<FixedBill> getByCustomerId(long customerId);

    @NotNull List<ProductHistory> getProducts(FixedBill bill);
}
