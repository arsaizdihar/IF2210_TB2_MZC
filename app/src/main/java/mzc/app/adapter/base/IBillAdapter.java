package mzc.app.adapter.base;

import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface IBillAdapter extends IBasicAdapter<Bill> {

    @NotNull Set<Bill> getByCustomerId(long memberId);

    @NotNull Set<ProductBill> getProducts(Bill bill);
}
