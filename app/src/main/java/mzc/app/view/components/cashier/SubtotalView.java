package mzc.app.view.components.cashier;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.SubtotalViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SubtotalViewModel.class)
public class SubtotalView extends BaseView<SubtotalViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getContainer();
    }
}
