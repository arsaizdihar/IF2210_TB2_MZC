package mzc.app.view.components.cashier;

import javafx.scene.Node;
import mzc.app.annotation.ModelInject;
import mzc.app.model.ProductBill;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.ProductViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ProductViewModel.class)
public class ProductView extends BaseView<ProductViewModel> {
    public ProductView(ProductBill productBill) {
        getViewModel().setProductBill(productBill);
    }

    @Override
    public @NotNull Node getView() {
        return null;
    }
}
