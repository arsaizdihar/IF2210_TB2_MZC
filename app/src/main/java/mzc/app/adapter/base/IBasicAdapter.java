package mzc.app.adapter.base;

import lombok.NonNull;

public interface IBasicAdapter<T> {
    T getById(long id);

    void persist(@NonNull T member);
}
