package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.Product;

class ProductAdapter extends FileProductAdapter<JSONLoader<Product>> {
    private static final @NonNull JSONLoader<Product> loader = new JSONLoader<>();

    @Override
    protected @NonNull JSONLoader<Product> getLoader() {
        return loader;
    }
}
