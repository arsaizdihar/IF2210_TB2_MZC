package mzc.app.adapter.obj;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.ProductBill;

class ProductBillAdapter extends FileProductBillAdapter<OBJLoader<ProductBill>> {
    private static final OBJLoader<ProductBill> loader = new OBJLoader<>();

    public ProductBillAdapter(@NonNull IProductAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected OBJLoader<ProductBill> getLoader() {
        return loader;
    }
}
