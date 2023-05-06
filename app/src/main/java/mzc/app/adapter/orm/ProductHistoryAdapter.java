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

    @Override
    public void persist(@NotNull ProductHistory item) {
        if (item.getId() == 0) {
            item.getBill().getProducts().add(item);
        }
        super.persist(item);
    }

    @Override
    public void delete(@NotNull ProductHistory model) {
        model.getBill().getProducts().removeIf(p -> p.getId() == model.getId());
        super.delete(model);
    }
}
