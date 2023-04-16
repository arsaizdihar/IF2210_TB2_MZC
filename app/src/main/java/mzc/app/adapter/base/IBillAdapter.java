package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;

import java.util.List;

public interface IBillAdapter extends IBasicAdapter<Bill> {

    @NonNull List<Bill> getByCustomerId(@NonNull Long memberId);

    @NonNull List<ProductBill> getProducts(Bill bill);
}
