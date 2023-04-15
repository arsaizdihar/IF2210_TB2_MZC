package mzc.app.adapter.orm;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.model.BaseModel;
import org.hibernate.Session;

abstract class ModelAdapter<T extends BaseModel> {
    @Getter protected final Session session;

    ModelAdapter(Session session) {
        this.session = session;
    }

    protected abstract @NonNull Class<T> getType();

    public T getById(Long id) {
        return session.get(getType(), id);
    }

    public void persist(@NonNull T item) {
        session.persist(item);
    }

}
