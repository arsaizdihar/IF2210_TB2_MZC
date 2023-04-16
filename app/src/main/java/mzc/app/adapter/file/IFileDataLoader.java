package mzc.app.adapter.file;

import lombok.NonNull;
import mzc.app.model.BaseModel;

import java.util.Map;

public interface IFileDataLoader<T extends BaseModel> {
    @NonNull Map<String, T> loadData(Class<T> model);

    void commit(Map<String, T> data, Class<T> model);
}
