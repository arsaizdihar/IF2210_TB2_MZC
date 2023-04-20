package mzc.app.adapter.file;

import lombok.AccessLevel;
import lombok.Getter;
import mzc.app.adapter.base.IBasicAdapter;
import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class FileModelAdapter<T extends BaseModel> implements IBasicAdapter<T> {

    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private final Map<String, T> data = getLoader().loadData(getType());

    @Getter
    private final @NotNull IFileDataLoader loader;

    protected FileModelAdapter(@NotNull IFileDataLoader loader) {
        this.loader = loader;

    }

    abstract protected Class<T> getType();

    public @NotNull Long getNewId() {
        if (getData().size() == 0) return 1L;
        Long maxId = getData().keySet().stream().map(Long::parseLong).max(Long::compare).get();
        return maxId + 1;
    }

    @Override
    public T getById(long id) {
        return getData().get(Long.toString(id));
    }

    public void persist(@NotNull T item) {
        if (item.getId() == 0) {
            item.setId(getNewId());
        }
        getData().put(Long.toString(item.getId()), item);
        commit();
    }

    @Override
    public @NotNull List<T> getAll() {
        return new ArrayList<>(getData().values());
    }

    public void commit() {
        getLoader().commit(getData(), getType());
    }
}
