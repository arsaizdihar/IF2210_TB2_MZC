package mzc.app.adapter.file;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;

public abstract class FileProductAdapter<T extends IFileDataLoader<Product>> extends FileModelAdapter<Product, T> implements IProductAdapter {
    @Override
    public Product getById(long id) {
        return super.getById(id);
    }

    @Override
    protected @NonNull Class<Product> getType() {
        return Product.class;
    }
}
