package mzc.app.adapter.json;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import mzc.app.model.BaseModel;

import java.util.Map;

abstract class ModelAdapter<T extends BaseModel> {
    @Getter(lazy = true, value = AccessLevel.PROTECTED) private final Map<String, T> data = loadData();

    protected abstract @NonNull Class<T> getType();

    private @NonNull Map<String, T> loadData() {
        return JSONLoader.loadDataFromFile(getType());
    }

    public T getById(Long id) {
        return getData().get(Long.toString(id));
    }

    public @NonNull Long getNewId() {
        if (getData().size() == 0) return 1L;
        Long maxId = getData().keySet().stream().map(Long::parseLong).max(Long::compare).get();
        return maxId + 1;
    }

    public void persist(@NonNull T item) {
        if (item.getId() == 0) {
            item.setId(getNewId());
        }
        getData().put(Long.toString(item.getId()), item);
        commit();
    }

    public void commit() {
        JSONLoader.saveDataToFile(getData(), getType());
    }
}
