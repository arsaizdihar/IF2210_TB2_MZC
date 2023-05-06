package mzc.app.view.components.product_list;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Product;
import mzc.app.view.components.member_list.EditMemberView;
import mzc.app.view.components.split_view.RightSideView;
import mzc.app.view_model.components.product_list.AddProductViewModel;
import mzc.app.view_model.components.product_list.EditProductViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(EditProductViewModel.class)
public class EditProductView extends RightSideView<EditProductViewModel> {
    public EditProductView(Product product) {
        getViewModel().setProduct(product);
    }
    @Override
    public @NotNull Node getView() {
        return getViewModel().getMain();
    }
}
