package mzc.app.adapter.base;

import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IProductAdapter extends IBasicAdapter<Product> {
    @NotNull Set<String> getCategories();
}
