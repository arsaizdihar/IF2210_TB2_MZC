package mzc.app.view.components.cashier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
//        getViewModel().getIncrement().setText("+");
//        getViewModel().getDecrement().setText("-");

        var product = getViewModel().getProductBill().getProduct();
        var container = getViewModel().getContainer();
        container.setMinWidth(0);
        container.setPrefWidth(1);
        var imageView = getViewModel().getImageView();
        imageView.setFitHeight(90);
        imageView.setFitWidth(90);
        container.getChildren().add(imageView);

        var productInfo = getViewModel().getProductInfo();
        var productNameText = new Text(product.getName());
        productNameText.setWrappingWidth(490);
        productNameText.getStyleClass().add("h4");
        productNameText.getStyleClass().add("b");
        productInfo.getChildren().add(productNameText);

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

        getViewModel().getMinus().setFitWidth(20);
        getViewModel().getMinus().setFitHeight(20);
        getViewModel().getPlus().setFitWidth(20);
        getViewModel().getPlus().setFitHeight(20);
        HBox icons = new HBox();
        icons.setAlignment(Pos.TOP_RIGHT);
        icons.setSpacing(10);

        getViewModel().getDecrement().setOpacity(0);
        getViewModel().getIncrement().setOpacity(0);
        getViewModel().getDecrement().setPrefSize(20, 20);
        getViewModel().getIncrement().setPrefSize(20, 20);
        var buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.TOP_RIGHT);
        buttonGroup.setSpacing(10);
        var counterLabel = getViewModel().getCounterLabel();
        icons.getChildren().addAll(getViewModel().getMinus(), getViewModel().getCounterLabel1(),getViewModel().getPlus());
        buttonGroup.getChildren().addAll(getViewModel().getDecrement(), counterLabel, getViewModel().getIncrement());
        getViewModel().getRoot().getChildren().add(container);
        getViewModel().getRoot().getChildren().add(icons);
        getViewModel().getRoot().getChildren().add(buttonGroup);
    }

    @Override
    public @NotNull Node getView() {
        return new StackPane(getViewModel().getRoot());
    }
}
