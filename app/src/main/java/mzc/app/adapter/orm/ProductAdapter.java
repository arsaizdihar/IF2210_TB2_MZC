package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.hibernate.Session;

class ProductAdapter extends ModelAdapter<Product> implements IProductAdapter {

    ProductAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NonNull Class<Product> getType() {
        return Product.class;
    }
}
