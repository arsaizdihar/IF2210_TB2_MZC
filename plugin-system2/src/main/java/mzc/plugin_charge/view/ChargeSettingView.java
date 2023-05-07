package mzc.plugin_charge.view;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.settings.SettingsTabView;
import mzc.plugin_charge.view_model.ChargeSettingViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ChargeSettingViewModel.class)
public class ChargeSettingView extends SettingsTabView<ChargeSettingViewModel> {
    public ChargeSettingView() {
        super("Tagihan Tambahan");
    }

    @Override
    public @NotNull Node getView() {
        var parent = super.getView();
        getViewModel().getSettingsBoxL().getChildren().add(getViewModel().getChargesView());
        return parent;
    }
}
