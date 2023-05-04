package mzc.app.view.components.cashier;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.CustomerSelectorViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(CustomerSelectorViewModel.class)
public class CustomerSelectorView extends BaseView<CustomerSelectorViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getCustomerSelector();
    }
}
