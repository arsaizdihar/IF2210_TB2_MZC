package mzc.app.view.components.cashier;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.ItemsViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ItemsViewModel.class)
public class ItemsView extends BaseView<ItemsViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getItemList();
    }
}
