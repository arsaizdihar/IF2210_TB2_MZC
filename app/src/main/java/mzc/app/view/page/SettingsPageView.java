package mzc.app.view.page;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.SettingsPageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SettingsPageViewModel.class)
public class SettingsPageView extends PageView<SettingsPageViewModel> {
    public @NotNull Node getView() {
        VBox root = new VBox();
        root.getChildren().addAll(getViewModel().getPaneMode());
        var tab = getViewModel().getPaneMode().getTabs().get(0);
        getViewModel().getPaneMode().getSelectionModel().select(tab);
        getViewModel().getPaneMode().setPrefWidth(1000);
        getViewModel().getPaneMode().sideProperty().set(Side.LEFT);
        return getViewModel().getPaneMode();
    }
}
