package mzc.app.adapter.xml;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.ProductBill;

class ProductBillAdapter extends FileProductBillAdapter<XMLLoader<ProductBill>> {
    private static final XMLLoader<ProductBill> loader = new XMLLoader<>();

    public ProductBillAdapter(@NonNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected XMLLoader<ProductBill> getLoader() {
        return loader;
    }
}