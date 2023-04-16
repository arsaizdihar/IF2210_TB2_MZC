package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.adapter.file.FileProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;

public class ProductHistoryBillAdapter extends FileProductHistoryBillAdapter<JSONLoader<ProductHistoryBill>> {
    private static final @NonNull JSONLoader<ProductHistoryBill> loader = new JSONLoader<>();

    public ProductHistoryBillAdapter(@NonNull ProductHistoryAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NonNull JSONLoader<ProductHistoryBill> getLoader() {
        return loader;
    }
}
