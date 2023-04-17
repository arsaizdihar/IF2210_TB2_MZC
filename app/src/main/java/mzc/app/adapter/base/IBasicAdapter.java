package mzc.app.adapter.base;

import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IBasicAdapter<T extends BaseModel> {
    T getById(long id);

    void persist(@NotNull T member);

    @NotNull List<T> getAll();
}
