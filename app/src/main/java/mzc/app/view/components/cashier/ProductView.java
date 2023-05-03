package mzc.app.view.components.cashier;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import mzc.app.annotation.ModelInject;
import mzc.app.model.ProductBill;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.components.cashier.ProductViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(ProductViewModel.class)
public class ProductView extends BaseView<ProductViewModel> {
    public ProductView(ProductBill productBill) {
        getViewModel().setProductBill(productBill);
        init();
    }

    public void init() {
        getViewModel().getIncrement().setText("+");
        getViewModel().getDecrement().setText("-");

        var product = getViewModel().getProductBill().getProduct();
        var container = getViewModel().getContainer();
        container.getChildren().add(new ImageView(product.getImage()));

        var productInfo = getViewModel().getProductInfo();
        productInfo.getChildren().add(new Label(product.getName()));
        productInfo.getChildren().add(new Label(product.getCategory()));
        productInfo.getChildren().add(new Label("Stok " + product.getStock()));
        productInfo.getChildren().add(new Label(product.getPriceView().toString()));

        container.getChildren().add(productInfo);

        var buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(getViewModel().getDecrement(), getViewModel().getCounterLabel(), getViewModel().getIncrement());
        container.getChildren().add(buttonGroup);
    }

    @Override
    public @NotNull Node getView() {
        return getViewModel().getContainer();
    }
}
