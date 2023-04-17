package mzc.app.adapter.xml;

import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

class ProductAdapter extends FileProductAdapter<XMLLoader<Product>> {
    private static final @NotNull XMLLoader<Product> loader = new XMLLoader<>();

    @Override
    protected @NotNull XMLLoader<Product> getLoader() {
        return loader;
    }
}
