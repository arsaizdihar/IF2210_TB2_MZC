package mzc.app.view.components.cashier;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.cashier.PaymentSummaryViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(PaymentSummaryViewModel.class)
public class PaymentSummaryView extends RightSideView<PaymentSummaryViewModel> {
    @Override
    public @NotNull Node getView() {
        return this.getViewModel().getContainer();
    }
}
