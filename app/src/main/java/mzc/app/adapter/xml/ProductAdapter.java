package mzc.app.adapter.xml;

import lombok.NonNull;
import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.Product;

class ProductAdapter extends FileProductAdapter<XMLLoader<Product>> {
    private static final @NonNull XMLLoader<Product> loader = new XMLLoader<>();

    @Override
    protected XMLLoader<Product> getLoader() {
        return loader;
    }
}
