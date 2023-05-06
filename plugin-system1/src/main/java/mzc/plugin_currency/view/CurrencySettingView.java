package mzc.plugin_currency.view;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.settings.SettingsTabView;
import mzc.plugin_currency.view_model.CurrencySettingViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(CurrencySettingViewModel.class)
public class CurrencySettingView extends SettingsTabView<CurrencySettingViewModel> {
    public CurrencySettingView() {
        super("Daftar Kurs", "Kurs Utama");
    }

    @Override
    public @NotNull Node getView() {
        var parent = super.getView();
        getViewModel().getSettingsBoxL().getChildren().add(getViewModel().getCurrenciesView());
        getViewModel().getSettingsBoxR().getChildren().add(getViewModel().getCurrencySelector());
        return parent;
    }
}
