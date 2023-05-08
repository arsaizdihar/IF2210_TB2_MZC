package mzc.app.view_model.components.cashier;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;

public class ItemsViewModel extends BaseViewModel {
    @Getter
    private VBox itemList = new VBox(15);

    @Getter
    private ScrollPane scrollable = new ScrollPane();

    @Override
    public void init() {
        super.init();
        scrollable.setContent(itemList);

        VBox.setVgrow(itemList, Priority.ALWAYS);

        var context = useContext(PaymentSummaryViewModel.PaymentSummaryContext.class).getValue();

        context.getProductItems().addListener(((observableValue, prev, productItems) -> {
            this.itemList.getChildren().clear();

            productItems.forEach((key, value) -> {
                var box = new HBox();
                var title = new Text(key.getProduct().getName());
                title.getStyleClass().add("h5");
                box.getChildren().add(title);
                var spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                box.getChildren().add(spacer);
                var price = new Text(value.toString());
                price.getStyleClass().add("h5");
                box.getChildren().add(price);
                itemList.getChildren().add(box);
            });

            System.out.println("Listening for product items update (ItemView)");
        }));
    }
}
