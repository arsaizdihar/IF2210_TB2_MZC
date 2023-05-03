package mzc.app.adapter.base;

import mzc.app.modules.setting.AppSettingManager;

public class AdapterConfig {
    protected static String pathOverride = null;

    public static String getBaseDataPath() {
        var type = AppSettingManager.get().getStorageMethod();
        return getBaseDataPath(type);
    }

    public static String getBaseDataPath(AdapterType type) {
        if (pathOverride != null) {
            return pathOverride;
        }

        String result = "data/";
        switch (type) {
            case JSON -> result = AppSettingManager.get().getJSONPath();
            case XML -> result = AppSettingManager.get().getXMLPath();
            case OBJ -> result = AppSettingManager.get().getOBJPath();
        }

        return result;
    }

    public static void setBaseDataPath(String path) {
        pathOverride = path;
    }
}
