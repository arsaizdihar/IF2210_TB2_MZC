package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;

class ProductAdapter extends ModelAdapter<Product> implements IProductAdapter {

    @Override
    public Product getById(long id) {
        return super.getById(id);
    }

    @Override
    protected @NonNull Class<Product> getType() {
        return Product.class;
    }
}
