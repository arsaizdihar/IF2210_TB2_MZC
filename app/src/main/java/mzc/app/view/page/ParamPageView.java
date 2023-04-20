package mzc.app.view.page;

import javafx.scene.Node;
import javafx.scene.control.Label;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.ParamPageViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ParamPageViewModel.class)
public class ParamPageView extends PageView<ParamPageViewModel> {

    public ParamPageView(String title) {
        getViewModel().setTitle(title);
    }
    @Override
    public @NotNull Node getView() {
        return new Label("Param Page");
    }
}
