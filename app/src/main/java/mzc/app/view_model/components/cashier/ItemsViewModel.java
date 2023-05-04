package mzc.app.view_model.components.cashier;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;

public class ItemsViewModel extends BaseViewModel {
    @Getter
    private VBox itemList = new VBox();

    @Override
    public void init() {
        super.init();

        var context = useContext(PaymentSummaryViewModel.PaymentSummaryContext.class).getValue();

        context.getProductItems().addListener(((observableValue, prev, productItems) -> {
            this.itemList.getChildren().clear();

            productItems.forEach((key, value) -> {
                itemList.getChildren().add(new Label(key.getProduct().getName() + " " + value.toString()));
            });

            System.out.println("Listening for product items update (ItemView)");
        }));
    }
}
