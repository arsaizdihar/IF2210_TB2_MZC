package mzc.app.bootstrap;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.Datastore;
import mzc.app.adapter.base.AdapterType;
import mzc.app.adapter.orm.SessionManager;
import mzc.app.modules.plugins.PluginLoader;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import mzc.app.view.components.settings.DataStoreView;
import mzc.app.view.components.settings.SettingsTabView;
import org.hibernate.cfg.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class App {
    @Getter
    protected @NonNull AppSetting appSetting;

    @Getter
    protected @NonNull Map<String, PageEntry> pages;

    @Getter
    protected @NonNull Map<String, Class<? extends SettingsTabView>> settingTabs;

    @Getter
    protected PluginLoader pluginLoader;

    @Getter
    protected Configuration hibernateConfiguration;

    public App() {
        try {
            Files.createDirectories(Paths.get("./data"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // initialize app setting
        this.appSetting = AppSettingManager.get();

        // bootstrap page list
        this.pages = PageEntryFactory.createPageEntries();

        // bootstrap setting tab list
        this.settingTabs = new HashMap<>();
        this.settingTabs.put("Data Store", DataStoreView.class);

        if (this.appSetting.getStorageMethod() == AdapterType.SQLORM) {
            this.hibernateConfiguration = SessionManager.getConfiguration();
        }

        // load adapter
        Datastore.initManager();
    }

    public void init() {
        this.pluginLoader = new PluginLoader();
        this.pluginLoader.loadPluginService();
        this.pluginLoader.loadPlugin(this);
    }

    public void postInit() {
        if (this.appSetting.getStorageMethod() == AdapterType.SQLORM) {
            this.pluginLoader.postLoadPlugin(SessionManager.getSession());
        } else {
            this.pluginLoader.postLoadPlugin(null);
        }
    }
}
