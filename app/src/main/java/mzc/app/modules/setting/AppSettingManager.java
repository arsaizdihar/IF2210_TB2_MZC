package mzc.app.modules.setting;

import lombok.NonNull;

public class AppSettingManager {
    private static AppSetting setting;

    public static @NonNull AppSetting get() {
        if (setting == null) {
            setting = AppSetting.load();
        }

        return setting;
    }
}
