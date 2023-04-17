package mzc.app.adapter.xml;

import mzc.app.adapter.file.FileProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;
import org.jetbrains.annotations.NotNull;

public class ProductHistoryBillAdapter extends FileProductHistoryBillAdapter<XMLLoader<ProductHistoryBill>> {
    private static final XMLLoader<ProductHistoryBill> loader = new XMLLoader<>();

    public ProductHistoryBillAdapter(@NotNull ProductHistoryAdapter productAdapter) {
        super(productAdapter);
    }

    @Override
    protected @NotNull XMLLoader<ProductHistoryBill> getLoader() {
        return loader;
    }
}
