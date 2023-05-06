package mzc.plugin_charge.view;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.RightSideView;
import mzc.plugin_charge.view_model.PaymentSummaryViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(PaymentSummaryViewModel.class)
public class PaymentSummaryView extends RightSideView<PaymentSummaryViewModel> {
    @Override
    public @NotNull Node getView() {
        return this.getViewModel().getContainer();
    }
}
