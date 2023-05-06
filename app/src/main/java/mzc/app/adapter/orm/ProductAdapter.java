package mzc.app.adapter.orm;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

class ProductAdapter extends ModelAdapter<Product> implements IProductAdapter {

    ProductAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<Product> getType() {
        return Product.class;
    }

    @Override
    public @NotNull Set<String> getCategories() {
        var cr = getSession().getCriteriaBuilder();
        var query = cr.createQuery(String.class);
        var root = query.from(Product.class);
        query.select(root.get("category")).distinct(true);
        return getSession().createQuery(query).stream().collect(Collectors.toSet());
        }
}
