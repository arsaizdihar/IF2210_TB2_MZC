package mzc.app.adapter.obj;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

class ProductBillAdapter extends FileProductBillAdapter<OBJLoader<ProductBill>> {
    private static final OBJLoader<ProductBill> loader = new OBJLoader<>();

    public ProductBillAdapter(@NotNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NotNull OBJLoader<ProductBill> getLoader() {
        return loader;
    }
}
