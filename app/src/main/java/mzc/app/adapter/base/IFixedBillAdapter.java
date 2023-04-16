package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.Bill;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistoryBill;

import java.util.List;

public interface IFixedBillAdapter extends IBasicAdapter<FixedBill> {
    @NonNull List<FixedBill> getByCustomerId(long customerId);

    @NonNull List<ProductHistoryBill> getProducts(FixedBill bill);
}
