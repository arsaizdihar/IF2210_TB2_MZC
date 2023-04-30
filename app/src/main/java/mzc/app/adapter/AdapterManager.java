package mzc.app.adapter;

import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
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
    public static Map<AdapterType, Class<? extends IMainAdapter>> getAvailableAdapters() {
//        use LinkedHashMap to make map still ordered
        Map<AdapterType, Class<? extends IMainAdapter>> map = new LinkedHashMap<>();
        map.put(AdapterType.JSON, JSONAdapter.class);
        map.put(AdapterType.XML, XMLAdapter.class);
        map.put(AdapterType.OBJ, OBJAdapter.class);
        map.put(AdapterType.SQLORM, ORMAdapter.class);
        return map;
    }
}
