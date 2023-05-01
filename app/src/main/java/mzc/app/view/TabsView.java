package mzc.app.view;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.TabsViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(TabsViewModel.class)
public class TabsView extends BaseView<TabsViewModel> {

    public TabsView() {
        super();
        getViewModel().getTabPane().setId("tab-pane");
        getViewModel().getTabPane();
    }

    @Override
    public @NotNull Node getView() {
        return getViewModel().getTabPane();
    }
}
