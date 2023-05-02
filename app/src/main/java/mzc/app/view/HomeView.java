package mzc.app.view;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.HomeViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(HomeViewModel.class)
public class HomeView extends BaseView<HomeViewModel> {

    @Override
    public @NotNull Node getView() {
        return new VBox(getViewModel().getMainCol());
    }
}
