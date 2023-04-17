package mzc.app.adapter.orm;

import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

class ProductHistoryAdapter extends ModelAdapter<ProductHistory> implements IProductHistoryAdapter {
    ProductHistoryAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<ProductHistory> getType() {
        return ProductHistory.class;
    }
}
