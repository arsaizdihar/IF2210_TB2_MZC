package mzc.app.view.page;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.HomePageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(HomePageViewModel.class)
public class HomePageView extends PageView<HomePageViewModel> {

    @Override
    public @NotNull Node getView() {
        return new VBox(getViewModel().getMainCol());
    }
}
