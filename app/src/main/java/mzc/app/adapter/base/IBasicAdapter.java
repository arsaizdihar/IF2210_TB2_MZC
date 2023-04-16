package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.BaseModel;

import java.util.List;

public interface IBasicAdapter<T extends BaseModel> {
    T getById(long id);

    void persist(@NonNull T member);

    @NonNull List<T> getAll();
}
