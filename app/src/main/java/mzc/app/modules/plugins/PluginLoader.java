package mzc.app.modules.plugins;

import lombok.NonNull;
import mzc.app.bootstrap.App;
import mzc.app.modules.setting.AppSettingManager;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ServiceLoader;

public class PluginLoader {
    protected @NonNull List<String> pluginFiles;
    protected ServiceLoader<Plugin> serviceLoader;

    public PluginLoader() {
        this.pluginFiles = AppSettingManager.get().getActivePlugins();
    }

    public void loadPluginService() {
        List<URL> urls = this.pluginFiles.stream().map(each -> {
            try {
                return (new File(each)).toURI().toURL();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getSystemClassLoader());

        this.serviceLoader = ServiceLoader.load(Plugin.class, classLoader);
    }

    public void loadPlugin(App app) {
        for (Plugin plugin : this.serviceLoader) {
            plugin.setup(app);
        }
    }

    public void postLoadPlugin() {
        for (Plugin plugin : this.serviceLoader) {
            plugin.postSetup();
        }
    }
}
