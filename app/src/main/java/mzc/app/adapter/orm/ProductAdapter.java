package mzc.app.adapter.orm;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

class ProductAdapter extends ModelAdapter<Product> implements IProductAdapter {

    ProductAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<Product> getType() {
        return Product.class;
    }
}
