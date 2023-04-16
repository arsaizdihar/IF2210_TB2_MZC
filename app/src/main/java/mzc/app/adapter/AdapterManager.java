package mzc.app.adapter;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.json.JSONAdapter;
import mzc.app.adapter.obj.OBJAdapter;
import mzc.app.adapter.orm.ORMAdapter;
import mzc.app.adapter.xml.XMLAdapter;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdapterManager {
    @Getter
    @NonNull IMainAdapter adapter;

    public AdapterManager() {
        adapter = new ORMAdapter();
    }

    public AdapterManager(@NonNull IMainAdapter adapter) {
        this.adapter = adapter;
    }

    public void setAdapter(@NonNull IMainAdapter adapter) {
        if (adapter != this.adapter) {
            this.adapter.close();
            this.adapter = adapter;
        }
    }

    @NonNull
    public static Map<String, Class<? extends IMainAdapter>> getAvailableAdapters() {
//        use LinkedHashMap to make map still ordered
        Map<String, Class<? extends IMainAdapter>> map = new LinkedHashMap<>();
        map.put("JSON", JSONAdapter.class);
        map.put("XML", XMLAdapter.class);
        map.put("OBJ", OBJAdapter.class);
        map.put("ORM", ORMAdapter.class);
        return map;
    }
}
