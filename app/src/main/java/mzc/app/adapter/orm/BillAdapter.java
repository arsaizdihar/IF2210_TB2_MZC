package mzc.app.adapter.orm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.model.Bill;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {

    BillAdapter(Session session) {
        super(session);
    }

    @Override
    public Bill getById(long id) {
        return super.getById(id);
    }

    @Override
    public @NonNull Set<Bill> getByMemberId(@NonNull Long memberId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bill> cr = cb.createQuery(Bill.class);
        Root<Bill> root = cr.from(Bill.class);
        cr.select(root).where(cb.equal(root.get("memberId"), memberId));
        return new LinkedHashSet<>(session.createQuery(cr).list());
    }

    @NonNull
    @Override
    protected Class<Bill> getType() {
        return Bill.class;
    }
}
