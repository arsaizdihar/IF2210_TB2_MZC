package mzc.app.adapter;

import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.json.JSONAdapter;
import mzc.app.adapter.obj.OBJAdapter;
import mzc.app.adapter.orm.ORMAdapter;
import mzc.app.adapter.sql.SQLAdapter;
import mzc.app.adapter.xml.XMLAdapter;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdapterManager {
    @Getter
    @NotNull IMainAdapter adapter;

    public AdapterManager() {
        AppSetting setting = AppSettingManager.get();

        switch (setting.getStorageMethod()) {
            case OBJ -> adapter = new OBJAdapter();
            case XML -> adapter = new XMLAdapter();
            case JSON -> adapter = new JSONAdapter();
            case SQLORM -> adapter = new ORMAdapter();
            case SQLRaw -> adapter = new SQLAdapter();
            default -> adapter = new JSONAdapter();
        }
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
        Map<AdapterType, Class<? extends IMainAdapter>> map = new LinkedHashMap<>();
        map.put(AdapterType.JSON, JSONAdapter.class);
        map.put(AdapterType.XML, XMLAdapter.class);
        map.put(AdapterType.OBJ, OBJAdapter.class);
        map.put(AdapterType.SQLORM, ORMAdapter.class);
        map.put(AdapterType.SQLRaw, SQLAdapter.class);
        return map;
    }
}
