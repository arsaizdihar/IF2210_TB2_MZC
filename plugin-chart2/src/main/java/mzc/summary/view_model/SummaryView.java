package mzc.summary.view_model;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.PageView;
import mzc.summary.view.SummaryViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SummaryViewModel.class)
public class SummaryView extends PageView<SummaryViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getContainer();
    }
}
