package mzc.app.adapter.xml;

import lombok.NonNull;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Bill;

class BillAdapter extends FileBillAdapter<XMLLoader<Bill>> {
    private static final XMLLoader<Bill> loader = new XMLLoader<>();
    private final ProductBillAdapter productBillAdapter;
    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull FileProductBillAdapter<?> getProductBillAdapter() {
        return productBillAdapter;
    }

    @Override
    protected XMLLoader<Bill> getLoader() {
        return loader;
    }
}
