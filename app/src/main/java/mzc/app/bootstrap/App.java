package mzc.app.bootstrap;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.AdapterType;
import mzc.app.adapter.orm.SessionManager;
import mzc.app.modules.plugins.PluginLoader;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import org.hibernate.cfg.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class App {
    @Getter
    protected @NonNull AppSetting appSetting;

    @Getter
    protected @NonNull Map<String, PageEntry> pages;

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

        if (this.appSetting.getStorageMethod() == AdapterType.SQLORM) {
            this.hibernateConfiguration = SessionManager.getConfiguration();
        }
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
