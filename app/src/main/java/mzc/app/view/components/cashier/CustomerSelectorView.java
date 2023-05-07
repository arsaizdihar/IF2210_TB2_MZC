package mzc.app.view.components.cashier;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.CustomerSelectorViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(CustomerSelectorViewModel.class)
public class CustomerSelectorView extends BaseView<CustomerSelectorViewModel> {
    @Override
    public @NotNull Node getView() {
        var container = getViewModel().getContainer().getView();
        var box = new VBox();
        box.getChildren().add(container);
//        box.setPrefWidth(200);
        box.setMinWidth(300);
        box.setMaxWidth(300);

        return box;
    }
}
