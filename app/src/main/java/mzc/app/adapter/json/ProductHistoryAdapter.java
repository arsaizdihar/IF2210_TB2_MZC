package mzc.app.adapter.json;

import mzc.app.adapter.file.FileProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

class ProductHistoryAdapter extends FileProductHistoryAdapter<JSONLoader<ProductHistory>> {
    private static final @NotNull JSONLoader<ProductHistory> loader = new JSONLoader<>();

    @Override
    protected @NotNull JSONLoader<ProductHistory> getLoader() {
        return loader;
    }
}
