package mzc.app.adapter.base;

import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public interface IBasicAdapter<T extends BaseModel> {
    T getById(long id);

    void persist(@NotNull T model);

    void deleteById(long id);

    void delete(@NotNull T model);

    @NotNull Set<T> getAll();

    @NotNull Set<T> getInIds(@NotNull Set<Long> ids);
}
