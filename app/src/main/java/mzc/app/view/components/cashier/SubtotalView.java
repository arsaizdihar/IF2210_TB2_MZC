package mzc.app.view.components.cashier;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.SubtotalViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(SubtotalViewModel.class)
public class SubtotalView extends BaseView<SubtotalViewModel> {
    private final VBox container = new VBox();

    public SubtotalView() {
        HBox info = new HBox();
        info.getChildren().add(getViewModel().getActionsContainer());
        info.getChildren().add(getViewModel().getPipelineContainer());

        getViewModel().getUsePointsCheckbox().setText("Gunakan poin");
        getViewModel().getCheckoutButton().setText("Checkout");
        getViewModel().getActionsContainer().getChildren().add(getViewModel().getUsePointsCheckbox());

        container.getChildren().add(info);
        container.getChildren().add(getViewModel().getCheckoutButton());
    }

    @Override
    public @NotNull Node getView() {
        return container;
    }
}
