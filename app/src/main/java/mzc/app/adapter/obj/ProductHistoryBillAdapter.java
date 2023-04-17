package mzc.app.adapter.obj;

import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.adapter.file.FileProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;
import org.jetbrains.annotations.NotNull;

public class ProductHistoryBillAdapter extends FileProductHistoryBillAdapter<OBJLoader<ProductHistoryBill>> {
    private static final OBJLoader<ProductHistoryBill> loader = new OBJLoader<>();

    public ProductHistoryBillAdapter(@NotNull IProductHistoryAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NotNull OBJLoader<ProductHistoryBill> getLoader() {
        return loader;
    }
}
