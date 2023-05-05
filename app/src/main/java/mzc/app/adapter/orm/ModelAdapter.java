package mzc.app.adapter.orm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import mzc.app.adapter.base.IBasicAdapter;
import mzc.app.model.BaseModel;
import mzc.app.model.ISoftDelete;
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
        delete(getById(id));
    }

    @Override
    public void delete(@NotNull T model) {
        if (model instanceof ISoftDelete) {
            ((ISoftDelete) model).setDeleted(true);
            Transaction tx = session.beginTransaction();
            session.persist(model);
            tx.commit();

        } else {
            var entityManager = session.getEntityManagerFactory().createEntityManager();
            var toDelete = entityManager.find(getClass(), Long.toString(model.getId()));
            entityManager.remove(toDelete);
            entityManager.flush();
            entityManager.clear();
            entityManager.close();
        }
    }

    @Override
    public @NotNull List<T> getInIds(@NotNull List<Long> ids) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(getType());
        cr.from(getType()).get("id").in(ids);

        if (ISoftDelete.class.isAssignableFrom(this.getType())) {
            return session.createQuery(cr).stream().filter(each -> {
                var model = (ISoftDelete) each;
                return !model.getDeleted();
            }).toList();
        }

        return session.createQuery(cr).list();
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

        if (ISoftDelete.class.isAssignableFrom(this.getType())) {
            return session.createQuery(cr).stream().filter(each -> {
                var model = (ISoftDelete) each;
                return !model.getDeleted();
            }).toList();
        }

        return session.createQuery(cr).list();
    }
}
