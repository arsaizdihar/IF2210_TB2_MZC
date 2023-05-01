package mzc.app_extend.modules.settings;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.setting.BaseSetting;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PluginSetting extends BaseSetting {
    @Getter
    @NonNull
    List<String> activePlugins;

    public PluginSetting(@NonNull List<String> activePlugins) {
        super(getDefaultPath());
        this.activePlugins = activePlugins;
    }

    private static String getDefaultPath() {
        return Paths.get("./setting.plugins").toAbsolutePath().toString();
    }

    public static PluginSetting load() {
        PluginSetting result;

        try {
            FileInputStream file = new FileInputStream(getDefaultPath());
            ObjectInputStream in = new ObjectInputStream(file);

            result = (PluginSetting) in.readObject();

            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException ignored) {
            result = new PluginSetting(
                    new ArrayList<>()
            );
        }

        return result;
    }
}
