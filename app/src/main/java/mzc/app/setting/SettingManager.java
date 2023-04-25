package mzc.app.setting;

import lombok.NonNull;

public class SettingManager {
    private static Setting setting;
    private static @NonNull Setting get() {
        if (setting == null) {
            setting = Setting.load();
        }

        return setting;
    }
}
