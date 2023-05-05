package mzc.app.adapter.base;

import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface IProductHistoryAdapter extends IBasicAdapter<ProductHistory> {
    @NotNull Set<ProductHistory> getByBillId(long id);
}
