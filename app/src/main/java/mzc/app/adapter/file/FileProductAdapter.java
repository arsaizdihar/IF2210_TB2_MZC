package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

public abstract class FileProductAdapter<T extends IFileDataLoader<Product>> extends FileModelAdapter<Product, T> implements IProductAdapter {

    @Override
    protected @NotNull Class<Product> getType() {
        return Product.class;
    }
}
