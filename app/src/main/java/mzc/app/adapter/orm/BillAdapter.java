package mzc.app.adapter.orm;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {

    BillAdapter(Session session) {
        super(session);
    }

    @Override
    public @NotNull Set<Bill> getByCustomerId(long customerId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
        Root<Bill> root = cr.from(Bill.class);
        cr.select(root).where(cb.equal(root.get("customerId"), customerId));
        return session.createQuery(cr).stream().collect(Collectors.toSet());
    }

    @Override
    public @NotNull Set<ProductBill> getProducts(@NotNull Bill bill) {
        return bill.getProducts();
    }

    @NotNull
    @Override
    protected Class<Bill> getType() {
        return Bill.class;
    }

    @Override
    public void persist(@NotNull Bill item) {
        if (item.getId() == 0) {
            item.getCustomer().getBills().add(item);
        }
        super.persist(item);
    }

    @Override
    public void delete(@NotNull Bill model) {
        model.getCustomer().getBills().remove(model);
        super.delete(model);
    }
}
