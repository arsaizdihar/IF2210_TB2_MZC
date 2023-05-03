package mzc.app.adapter.orm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import mzc.app.adapter.base.IBasicAdapter;
import mzc.app.model.BaseModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ModelAdapter<T extends BaseModel> implements IBasicAdapter<T> {
    @Getter
    protected final Session session;

    public ModelAdapter(Session session) {
        this.session = session;
    }

    protected abstract @NotNull Class<T> getType();

    @Override
    public T getById(long id) {
        return session.get(getType(), id);
    }

    @Override
    public void deleteById(long id) {
        var entityManager = session.getEntityManagerFactory().createEntityManager();
        var model = entityManager.find(getClass(), id);
        entityManager.remove(model);
        entityManager.flush();
        entityManager.clear();
        entityManager.close();
    }

    @Override
    public void delete(@NotNull T model) {
        deleteById(model.getId());
    }

    public void persist(@NotNull T item) {
        Transaction tx = session.beginTransaction();
        session.persist(item);
        tx.commit();
    }

    @Override
    public @NotNull List<T> getAll() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(getType());
        cr.from(getType());
        return session.createQuery(cr).list();
    }

}
