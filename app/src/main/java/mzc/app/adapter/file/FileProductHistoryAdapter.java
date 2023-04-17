package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

public class FileProductHistoryAdapter extends FileModelAdapter<ProductHistory> implements IProductHistoryAdapter {

    protected FileProductHistoryAdapter(@NotNull IFileDataLoader loader) {
        super(loader);
    }

    @Override
    protected Class<ProductHistory> getType() {
        return ProductHistory.class;
    }
}
