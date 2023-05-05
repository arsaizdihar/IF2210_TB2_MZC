package mzc.app.adapter.orm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FixedBillAdapter extends ModelAdapter<FixedBill> implements IFixedBillAdapter {

    FixedBillAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<FixedBill> getType() {
        return FixedBill.class;
    }

    @Override
    public @NotNull Set<FixedBill> getByCustomerId(long customerId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<FixedBill> cr = cb.createQuery(FixedBill.class);
        Root<FixedBill> root = cr.from(FixedBill.class);
        cr.select(root).where(cb.equal(root.get("customerId"), customerId));
        return session.createQuery(cr).stream().collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public @NotNull Set<ProductHistory> getProducts(FixedBill bill) {
        return bill.getProducts();
    }

    @Override
    public void persist(@NotNull FixedBill item) {
        if (item.getId() == 0) {
            item.getCustomer().getFixedBills().add(item);
        }
        super.persist(item);
    }

    @Override
    public void delete(@NotNull FixedBill model) {
        model.getCustomer().getFixedBills().remove(model);
        super.delete(model);
    }
}
