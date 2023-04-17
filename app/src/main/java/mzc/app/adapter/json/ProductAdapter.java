package mzc.app.adapter.json;

import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

class ProductAdapter extends FileProductAdapter<JSONLoader<Product>> {
    private static final @NotNull JSONLoader<Product> loader = new JSONLoader<>();

    @Override
    protected @NotNull JSONLoader<Product> getLoader() {
        return loader;
    }
}
