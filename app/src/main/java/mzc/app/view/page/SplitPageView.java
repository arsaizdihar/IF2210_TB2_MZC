package mzc.app.view.page;

import javafx.scene.Node;
import mzc.app.view.base.PageView;
import mzc.app.view_model.page.SplitPageViewModel;
import org.jetbrains.annotations.NotNull;

public class SplitPageView<T extends SplitPageViewModel> extends PageView<T> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getSplitView();
    }
}
