package mzc.app.adapter.orm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import org.hibernate.Session;

import java.util.List;

class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {

    BillAdapter(Session session) {
        super(session);
    }

    @Override
    public Bill getById(long id) {
        return super.getById(id);
    }

    @Override
    public @NonNull List<Bill> getByCustomerId(@NonNull Long customerId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
        Root<Bill> root = cr.from(Bill.class);
        cr.select(root).where(cb.equal(root.get("customerId"), customerId));
        return session.createQuery(cr).list();
    }

    @Override
    public @NonNull List<Bill> getAll() {
        return session.createQuery("SELECT b FROM Bill b", Bill.class).list();
    }

    @Override
    public @NonNull List<ProductBill> getProducts(Bill bill) {
        return bill.getProducts();
    }

    @NonNull
    @Override
    protected Class<Bill> getType() {
        return Bill.class;
    }
}
