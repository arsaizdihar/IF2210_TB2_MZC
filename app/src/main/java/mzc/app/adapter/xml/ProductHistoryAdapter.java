package mzc.app.adapter.xml;

import mzc.app.adapter.file.FileProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

class ProductHistoryAdapter extends FileProductHistoryAdapter<XMLLoader<ProductHistory>> {
    private static final XMLLoader<ProductHistory> loader = new XMLLoader<>();

    @Override
    protected @NotNull XMLLoader<ProductHistory> getLoader() {
        return loader;
    }
}
