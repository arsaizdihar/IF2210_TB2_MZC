package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class FileProductAdapter extends FileModelAdapter<Product> implements IProductAdapter {
    protected FileProductAdapter(@NotNull IFileDataLoader loader) {
        super(loader);
    }

    @Override
    protected @NotNull Class<Product> getType() {
        return Product.class;
    }

    @Override
    public @NotNull Set<String> getCategories() {
        return getData().values().stream().map(Product::getCategory).collect(Collectors.toSet());
    }
}
