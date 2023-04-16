package mzc.app.adapter.xml;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileFixedBillAdapter;
import mzc.app.model.FixedBill;

class FixedBillAdapter extends FileFixedBillAdapter<XMLLoader<FixedBill>> {
    private static final XMLLoader<FixedBill> loader = new XMLLoader<>();

    @Getter
    private final ProductHistoryBillAdapter productBillAdapter;

    FixedBillAdapter(ProductHistoryAdapter productAdapter) {
        this.productBillAdapter = new ProductHistoryBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull XMLLoader<FixedBill> getLoader() {
        return loader;
    }
}
