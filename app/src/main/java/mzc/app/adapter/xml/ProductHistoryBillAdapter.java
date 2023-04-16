package mzc.app.adapter.xml;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;

public class ProductHistoryBillAdapter extends FileProductHistoryBillAdapter<XMLLoader<ProductHistoryBill>> {
    private static final XMLLoader<ProductHistoryBill> loader = new XMLLoader<>();

    public ProductHistoryBillAdapter(@NonNull ProductHistoryAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NonNull XMLLoader<ProductHistoryBill> getLoader() {
        return loader;
    }
}
