package mzc.app.adapter.file;

import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface IFileDataLoader<T extends BaseModel> {
    @NotNull Map<String, T> loadData(Class<T> model);

    void commit(Map<String, T> data, Class<T> model);
}
