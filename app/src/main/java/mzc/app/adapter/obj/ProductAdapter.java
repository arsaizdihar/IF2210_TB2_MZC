package mzc.app.adapter.obj;

import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

class ProductAdapter extends FileProductAdapter<OBJLoader<Product>> {
    private static final OBJLoader<Product> loader = new OBJLoader<>();

    @Override
    protected @NotNull OBJLoader<Product> getLoader() {
        return loader;
    }
}
