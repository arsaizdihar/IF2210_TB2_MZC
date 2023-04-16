package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Product;
import mzc.app.model.ProductBill;


class ProductBillAdapter extends FileProductBillAdapter<JSONLoader<ProductBill>> {
    private static final @NonNull JSONLoader<ProductBill> loader = new JSONLoader<>();

    public ProductBillAdapter(@NonNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NonNull JSONLoader<ProductBill> getLoader() {
        return loader;
    }
}
