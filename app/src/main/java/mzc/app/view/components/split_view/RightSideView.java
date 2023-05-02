package mzc.app.view.components.split_view;

import javafx.scene.Node;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import org.jetbrains.annotations.NotNull;

public  class RightSideView<T extends RightSideViewModel> extends BaseView<T> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getEmptyLabel();
    }
}
