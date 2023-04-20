package mzc.app.adapter.base;

import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IProductHistoryAdapter extends IBasicAdapter<ProductHistory> {
    @NotNull List<ProductHistory> getByBillId(long id);
}
