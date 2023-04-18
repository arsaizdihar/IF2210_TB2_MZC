package mzc.app.adapter;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.json.JSONAdapter;
import mzc.app.adapter.obj.OBJAdapter;
import mzc.app.adapter.orm.ORMAdapter;
import mzc.app.adapter.xml.XMLAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdapterManager {
    @Getter
    @NotNull IMainAdapter adapter;

    public AdapterManager() {
        adapter = new JSONAdapter();
    }

    public AdapterManager(@NotNull IMainAdapter adapter) {
        this.adapter = adapter;
    }

    public void setAdapter(@NotNull IMainAdapter adapter) {
        if (adapter != this.adapter) {
            this.adapter.close();
            this.adapter = adapter;
        }
    }

    @NotNull
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
