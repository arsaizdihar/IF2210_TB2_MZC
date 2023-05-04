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

        context.getProductItems().addListener(observable -> {
            itemList.getChildren().clear();

            context.getProductItems().getValue().forEach(pair -> {
                itemList.getChildren().add(new Label(pair.x.getProduct().getName() + " " + pair.y.toString()));
            });

            System.out.println("Listeing for product items update (ItemView)");
        });
    }
}
