package mzc.plugin_charge.view;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.plugin_charge.view_model.SubtotalViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SubtotalViewModel.class)
public class SubtotalView extends BaseView<SubtotalViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getContainer();
    }
}