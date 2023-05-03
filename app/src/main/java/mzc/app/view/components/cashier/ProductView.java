package mzc.app.view.components.cashier;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        container.setMinWidth(0);
        container.setPrefWidth(1);
        var imageView = new ImageView(product.getImage());
        imageView.setFitHeight(125);
        imageView.setFitWidth(125);
        container.getChildren().add(imageView);

        var productInfo = getViewModel().getProductInfo();
        var titleLabel = new Label(product.getName());
        titleLabel.getStyleClass().add("h4");
        titleLabel.getStyleClass().add("b");
        productInfo.getChildren().add(titleLabel);

        var categoryLabel = new Label(product.getCategory());
        categoryLabel.getStyleClass().add("h5");
        productInfo.getChildren().add(categoryLabel);

        var stockLabel = new Label("Stok " + product.getStock());
        stockLabel.getStyleClass().add("h5");
        productInfo.getChildren().add(stockLabel);

        var priceLabel = new Label(product.getPriceView().toString());
        priceLabel.getStyleClass().add("h5");
        productInfo.getChildren().add(priceLabel);
        HBox.setHgrow(productInfo, Priority.ALWAYS);

        productInfo.setPadding(new Insets(0, 0, 0, 10));

        container.getChildren().add(productInfo);

        var buttonGroup = new HBox();
        buttonGroup.setSpacing(10);
        var counterLabel = getViewModel().getCounterLabel();
        buttonGroup.getChildren().addAll(getViewModel().getDecrement(), counterLabel, getViewModel().getIncrement());
        container.getChildren().add(buttonGroup);
    }

    @Override
    public @NotNull Node getView() {
        return getViewModel().getContainer();
    }
}
