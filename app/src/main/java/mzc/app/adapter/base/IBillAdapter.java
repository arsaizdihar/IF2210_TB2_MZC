package mzc.app.adapter.base;

import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IBillAdapter extends IBasicAdapter<Bill> {

    @NotNull List<Bill> getByCustomerId(long memberId);

    @NotNull List<ProductBill> getProducts(Bill bill);
}
