package mzc.app.plugins.loader;

import lombok.NonNull;
import mzc.app.modules.setting.AppSetting;

import java.util.List;

public class ModuleLoader {
    private final @NonNull AppSetting setting;

    public ModuleLoader(@NonNull AppSetting setting) {
        this.setting = setting;
    }

    public void loadModule() {
        List<String> plugins = this.setting.getActivePlugins();
    }
}
