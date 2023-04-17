package mzc.app.adapter.json;

import mzc.app.adapter.file.FileProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;
import org.jetbrains.annotations.NotNull;

public class ProductHistoryBillAdapter extends FileProductHistoryBillAdapter<JSONLoader<ProductHistoryBill>> {
    private static final @NotNull JSONLoader<ProductHistoryBill> loader = new JSONLoader<>();

    public ProductHistoryBillAdapter(@NotNull ProductHistoryAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NotNull JSONLoader<ProductHistoryBill> getLoader() {
        return loader;
    }
}
