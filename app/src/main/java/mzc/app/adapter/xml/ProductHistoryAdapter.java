package mzc.app.adapter.xml;

import lombok.NonNull;
import mzc.app.adapter.file.FileProductHistoryAdapter;
import mzc.app.model.ProductHistory;

class ProductHistoryAdapter extends FileProductHistoryAdapter<XMLLoader<ProductHistory>> {
    private static final XMLLoader<ProductHistory> loader = new XMLLoader<>();

    @Override
    protected @NonNull XMLLoader<ProductHistory> getLoader() {
        return loader;
    }
}
