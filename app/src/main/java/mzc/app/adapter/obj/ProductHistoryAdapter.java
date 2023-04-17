package mzc.app.adapter.obj;

import mzc.app.adapter.file.FileProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

class ProductHistoryAdapter extends FileProductHistoryAdapter<OBJLoader<ProductHistory>> {
    private static final OBJLoader<ProductHistory> loader = new OBJLoader<>();

    @Override
    protected @NotNull OBJLoader<ProductHistory> getLoader() {
        return loader;
    }
}
