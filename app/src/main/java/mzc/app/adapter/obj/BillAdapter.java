package mzc.app.adapter.obj;

import lombok.NonNull;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Bill;

class BillAdapter extends FileBillAdapter<OBJLoader<Bill>> {
    private static final OBJLoader<Bill> loader = new OBJLoader<>();
    private final ProductBillAdapter productBillAdapter;

    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull FileProductBillAdapter<?> getProductBillAdapter() {
        return productBillAdapter;
    }

    @Override
    protected OBJLoader<Bill> getLoader() {
        return loader;
    }
}
