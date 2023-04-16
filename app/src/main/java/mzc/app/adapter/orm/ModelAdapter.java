package mzc.app.adapter.orm;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IBasicAdapter;
import mzc.app.model.BaseModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

abstract class ModelAdapter<T extends BaseModel> implements IBasicAdapter<T> {
    @Getter protected final Session session;

    ModelAdapter(Session session) {
        this.session = session;
    }

    protected abstract @NonNull Class<T> getType();

    @Override
    public T getById(long id) {
        return session.get(getType(), id);
    }

    public void persist(@NonNull T item)
    {
        Transaction tx = session.beginTransaction();
        session.persist(item);
        tx.commit();
    }

    @Override
    public @NonNull List<T> getAll() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(getType());
        cr.from(getType());
        return session.createQuery(cr).list();
    }

}
