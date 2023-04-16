package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistoryBill;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FixedBillAdapter extends ModelAdapter<FixedBill> implements IFixedBillAdapter {

    FixedBillAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NonNull Class<FixedBill> getType() {
        return FixedBill.class;
    }

    @Override
    public @NonNull List<FixedBill> getByCustomerId(long customerId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<FixedBill> cr = cb.createQuery(FixedBill.class);
        Root<FixedBill> root = cr.from(FixedBill.class);
        cr.select(root).where(cb.equal(root.get("customerId"), customerId));
        return session.createQuery(cr).list();
    }

    @Override
    public @NonNull List<ProductHistoryBill> getProducts(FixedBill bill) {
        return bill.getProducts();
    }
}
