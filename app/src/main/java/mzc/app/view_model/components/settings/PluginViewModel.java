package mzc.app.view_model.components.settings;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import org.controlsfx.control.ToggleSwitch;

public class PluginViewModel extends SettingsTabViewModel {
    @Getter
    private final VBox pluginList = new VBox();
    @Getter
    private AppSetting setting = AppSettingManager.get();
    @Getter
    private final Button pluginsButton = new Button("Simpan Perubahan");

    public PluginViewModel() { super(); }
    @Override
    public void init() {
        super.init();
        setting.getActivePlugins().forEach(plugin -> {
            Label label = new Label(plugin.toString());
            ToggleSwitch toggleSwitch = new ToggleSwitch(plugin.toString());
            pluginList.getChildren().add(label);
            pluginList.getChildren().add(toggleSwitch);
        });
    }
}
