package mzc.app.bootstrap;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;

import java.util.Map;

public class App {
    @Getter
    protected @NonNull AppSetting appSetting;

    @Getter
    protected @NonNull Map<String, PageEntry> pages;

    public App() {
        // initialize app setting
        this.appSetting = AppSettingManager.get();

        // bootstrap page list
        this.pages = PageEntryFactory.createPageEntries();
    }
}
