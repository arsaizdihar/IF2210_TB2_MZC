package mzc.app.view.page;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.MainPageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MainPageViewModel.class)
public class MainPageView extends PageView<MainPageViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getButton();
    }
}
