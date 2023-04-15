package mzc.app.adapter;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.orm.ORMAdapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AdapterManager {
    @Getter IMainAdapter adapter;

    public AdapterManager() {
        adapter = new ORMAdapter();
    }

    public void setAdapter(IMainAdapter adapter) {
        if (adapter != this.adapter) {
            this.adapter.close();
            this.adapter = adapter;
        }
    }

    @NonNull
    public static Map<String, Class<? extends IMainAdapter>> getAvailableAdapters() {
//        use LinkedHashMap to make map still ordered
        Map<String, Class<? extends IMainAdapter>> map = new LinkedHashMap<>();
        map.put("ORM", ORMAdapter.class);
        return map;
    }
}
