package mzc.app.adapter.obj;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileProductHistoryAdapter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;

class ProductHistoryAdapter extends FileProductHistoryAdapter<OBJLoader<ProductHistory>> {
    private static final OBJLoader<ProductHistory> loader = new OBJLoader<>();

    @Override
    protected @NonNull OBJLoader<ProductHistory> getLoader() {
        return loader;
    }
}
