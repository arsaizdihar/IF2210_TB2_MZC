package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.ProductBill;


class ProductBillAdapter extends FileProductBillAdapter<JSONLoader<ProductBill>> {
    private static final JSONLoader<ProductBill> loader = new JSONLoader<>();

    public ProductBillAdapter(@NonNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected JSONLoader<ProductBill> getLoader() {
        return loader;
    }
}
