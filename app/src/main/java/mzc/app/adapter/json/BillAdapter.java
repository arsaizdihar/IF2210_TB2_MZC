package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Bill;

class BillAdapter extends FileBillAdapter<JSONLoader<Bill>> {
    private static final JSONLoader<Bill> loader = new JSONLoader<>();
    private final ProductBillAdapter productBillAdapter;
    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull FileProductBillAdapter<?> getProductBillAdapter() {
        return productBillAdapter;
    }

    @Override
    protected JSONLoader<Bill> getLoader() {
        return loader;
    }
}
