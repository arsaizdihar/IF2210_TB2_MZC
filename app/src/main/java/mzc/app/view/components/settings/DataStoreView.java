package mzc.app.view.components.settings;

import javafx.scene.Node;
import javafx.scene.control.Button;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.settings.SettingsTabView;
import mzc.app.view_model.components.settings.DataStoreViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(DataStoreViewModel.class)
public class DataStoreView extends SettingsTabView<DataStoreViewModel> {
    public DataStoreView() {
        super("Metode Penyimpanan", "Lokasi Penyimpanan");
    }

    @Override
    @NotNull
    public Node getView() {
        getViewModel().getLeftBox().getChildren().addAll(getViewModel().getSelectMethod());
        getViewModel().getRightBox().getChildren().addAll(getViewModel().getStorageLocation());
        return super.getView();
    }

}
