package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileProductHistoryAdapter;
import mzc.app.model.ProductHistory;

class ProductHistoryAdapter extends FileProductHistoryAdapter<JSONLoader<ProductHistory>> {
    private static final @NonNull JSONLoader<ProductHistory> loader = new JSONLoader<>();

    @Override
    protected @NonNull JSONLoader<ProductHistory> getLoader() {
        return loader;
    }
}
