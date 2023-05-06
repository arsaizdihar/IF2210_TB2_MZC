package mzc.app.view.components.product_list;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Product;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.product_list.ProductDisplayViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ProductDisplayViewModel.class)
public class ProductDisplayView extends BaseView<ProductDisplayViewModel> {

    public ProductDisplayView(Product product) {
        getViewModel().setter(product);
    }
    @Override
    public @NotNull Node getView() {
        return getViewModel().getMain();
    }
}
