package mzc.app.adapter.xml;

import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Bill;
import org.jetbrains.annotations.NotNull;

class BillAdapter extends FileBillAdapter<XMLLoader<Bill>> {
    private static final XMLLoader<Bill> loader = new XMLLoader<>();
    private final ProductBillAdapter productBillAdapter;
    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NotNull FileProductBillAdapter<?> getProductBillAdapter() {
        return productBillAdapter;
    }

    @Override
    protected @NotNull XMLLoader<Bill> getLoader() {
        return loader;
    }
}
