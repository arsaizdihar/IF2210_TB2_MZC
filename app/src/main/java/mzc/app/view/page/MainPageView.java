package mzc.app.view.page;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.MainPageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MainPageViewModel.class)
public class MainPageView extends PageView<MainPageViewModel> {
    @Override
    public @NotNull Node getView() {
        VBox root = new VBox();
        root.getChildren().addAll(getViewModel().getTextField(), getViewModel().getLabel(), getViewModel().getCounterButton(), getViewModel().getCounterLabel(), getViewModel().getChangePageButton(), getViewModel().getFileDialogView().getView());
        return root;
    }
}
