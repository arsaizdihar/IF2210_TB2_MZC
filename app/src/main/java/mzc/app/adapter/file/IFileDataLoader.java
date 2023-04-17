package mzc.app.adapter.file;

import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface IFileDataLoader {
    @NotNull <T extends BaseModel> Map<String, T> loadData(Class<T> model);

    <T extends BaseModel> void  commit(Map<String, T> data, Class<T> model);
}
