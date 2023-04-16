package mzc.app.adapter.obj;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.adapter.file.FileProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;

public class ProductHistoryBillAdapter extends FileProductHistoryBillAdapter<OBJLoader<ProductHistoryBill>> {
    private static final OBJLoader<ProductHistoryBill> loader = new OBJLoader<>();

    public ProductHistoryBillAdapter(@NonNull IProductHistoryAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NonNull OBJLoader<ProductHistoryBill> getLoader() {
        return loader;
    }
}
