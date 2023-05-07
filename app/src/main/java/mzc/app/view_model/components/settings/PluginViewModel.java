package mzc.app.view_model.components.settings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import mzc.app.view.components.FileDialogView;

public class PluginViewModel extends SettingsTabViewModel {
    @Getter
    private final VBox pluginList = new VBox();
    @Getter
    private AppSetting setting = AppSettingManager.get();
    @Getter
    FileDialogView addPluginDialog = new FileDialogView(new Button("Tambah Plugin"),
            file -> {
                String absolutePath = file.getAbsolutePath();
                setting.getActivePlugins().add(absolutePath);
                reloadList();
            }, "jar", "*.jar");

    public PluginViewModel() {
        super();
    }

    @Override
    public void init() {
        super.init();
        createView(addPluginDialog);
        reloadList();
        getSaveButtonL().setOnAction(
                event -> {
                    setting.save();
                    showInfoAlert(true);
                }
        );
    }

    public void reloadList() {
        pluginList.getChildren().clear();
        setting.getActivePlugins().forEach(plugin -> {
            Label label = new Label(plugin);
            pluginList.getChildren().add(label);
        });
    }
}
