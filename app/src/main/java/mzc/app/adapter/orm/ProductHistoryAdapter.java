package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.hibernate.Session;

class ProductHistoryAdapter extends ModelAdapter<ProductHistory> implements IProductHistoryAdapter {
    ProductHistoryAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NonNull Class<ProductHistory> getType() {
        return ProductHistory.class;
    }
}
