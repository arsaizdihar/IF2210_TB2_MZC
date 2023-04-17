package mzc.app.adapter.xml;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

class ProductBillAdapter extends FileProductBillAdapter<XMLLoader<ProductBill>> {
    private static final XMLLoader<ProductBill> loader = new XMLLoader<>();

    public ProductBillAdapter(@NotNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NotNull XMLLoader<ProductBill> getLoader() {
        return loader;
    }
}