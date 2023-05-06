package mzc.app.view.components.settings;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view_model.components.settings.PluginViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(PluginViewModel.class)
public class PluginView extends SettingsTabView<PluginViewModel>{
    public PluginView() { super("Daftar Plugin yang Aktif"); }

    @Override
    @NotNull
    public Node getView() {
        var root = super.getView();
        getViewModel().getSettingsBoxL().getChildren().addAll(getViewModel().getPluginList(), getViewModel().getAddPluginDialog().getView());
        return root;
    }
}
