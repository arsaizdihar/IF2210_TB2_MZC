package mzc.app.adapter.orm;

import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

class ProductHistoryAdapter extends ModelAdapter<ProductHistory> implements IProductHistoryAdapter {
    ProductHistoryAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<ProductHistory> getType() {
        return ProductHistory.class;
    }

    @Override
    public @NotNull Set<ProductHistory> getByBillId(long id) {
        return session.createQuery("FROM ProductHistory WHERE billId = :id", ProductHistory.class)
                .setParameter("id", id)
                .stream().collect(Collectors.toSet());
    }
}
