package mzc.app.adapter.obj;

import lombok.NonNull;
import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.Product;

class ProductAdapter extends FileProductAdapter<OBJLoader<Product>> {
    private static final @NonNull OBJLoader<Product> loader = new OBJLoader<>();

    @Override
    protected OBJLoader<Product> getLoader() {
        return loader;
    }
}
