package mzc.app.adapter.json;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;


class ProductBillAdapter extends FileProductBillAdapter<JSONLoader<ProductBill>> {
    private static final @NotNull JSONLoader<ProductBill> loader = new JSONLoader<>();

    public ProductBillAdapter(@NotNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NotNull JSONLoader<ProductBill> getLoader() {
        return loader;
    }
}
