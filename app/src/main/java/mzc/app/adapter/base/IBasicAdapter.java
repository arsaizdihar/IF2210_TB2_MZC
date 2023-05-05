package mzc.app.adapter.base;

import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IBasicAdapter<T extends BaseModel> {
    T getById(long id);

    void persist(@NotNull T model);

    void deleteById(long id);

    void delete(@NotNull T model);

    @NotNull List<T> getAll();

    @NotNull List<T> getInIds(@NotNull List<Long> ids);
}
