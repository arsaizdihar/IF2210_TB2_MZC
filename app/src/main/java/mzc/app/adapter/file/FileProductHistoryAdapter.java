package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;

public abstract class FileProductHistoryAdapter <T extends IFileDataLoader<ProductHistory>> extends FileModelAdapter<ProductHistory, T> implements IProductHistoryAdapter {
    @Override
    protected Class<ProductHistory> getType() {
        return ProductHistory.class;
    }
}
