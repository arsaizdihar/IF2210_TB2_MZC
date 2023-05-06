package mzc.app.view.components.product_list;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.product_list.AddProductViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(AddProductViewModel.class)
public class AddProductView extends RightSideView<AddProductViewModel> {
    @Override
    public @NotNull Node getView() {
        return getViewModel().getMain();
    }
}
