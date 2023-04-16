package mzc.app.adapter.obj;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileProductAdapter;
import mzc.app.model.FixedBill;
import mzc.app.model.Product;

class ProductAdapter extends FileProductAdapter<OBJLoader<Product>> {
    private static final OBJLoader<Product> loader = new OBJLoader<>();

    @Override
    protected @NonNull OBJLoader<Product> getLoader() {
        return loader;
    }
}
