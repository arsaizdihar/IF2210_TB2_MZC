package mzc.app_extend.modules.settings;

import lombok.NonNull;

public class PluginSettingManager {
    private static PluginSetting setting;

    public static @NonNull PluginSetting get() {
        if (setting == null) {
            setting = PluginSetting.load();
        }

        return setting;
    }
}
