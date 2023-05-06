package mzc.app.view_model.components.cashier;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.model.ProductBill;
import mzc.app.utils.reactive.State;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.CashierPageViewModel;
import org.jetbrains.annotations.NotNull;

public class ProductViewModel extends BaseViewModel {
    @Getter
    private ProductBill productBill;

    @Getter
    private final @NotNull Label counterLabel = new Label("");

    @Getter
    private final @NotNull State<Integer> counter = new State<Integer>(0);

    @Getter
    private final @NotNull Button increment = new Button();

    @Getter
    private final @NotNull Button decrement = new Button();

    @Getter
    private final @NotNull HBox container = new HBox();

    @Getter
    private final @NotNull VBox productInfo = new VBox();

    @Override
    public void init() {
        super.init();

        this.counterLabel.textProperty().bind(Bindings.createObjectBinding(() -> counter.getValue().toString(), counter));

        this.increment.setOnAction(e -> {
            counter.setValue(counter.getValue() + 1);
        });

        this.decrement.setOnAction(e -> {
            if (counter.getValue() > 0) {
                counter.setValue(counter.getValue() - 1);
            }
        });

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();

        this.counter.addListener((observableValue, prev, next) -> {
            if (next == 0) {
                this.decrement.setDisable(true);
            }

            if (next == this.productBill.getProduct().getStock()) {
                this.increment.setDisable(true);
            }

            if (next > 0 && this.decrement.isDisabled()) {
                this.decrement.setDisable(false);
            }

            if (next < this.productBill.getProduct().getStock() && this.increment.isDisabled()) {
                this.increment.setDisable(false);
            }

            this.productBill.setAmount(next);
            getAdapter().getProductBill().persist(this.productBill);
            System.out.println("Updating counter. Telling to update summary.");

            var oldBill = cashierContext.getBill().getValue();
            var newBill = getAdapter().getBill().getById(oldBill.getId());

            if (newBill.getCustomerId() != oldBill.getCustomerId()) {
                throw new RuntimeException("Different customer id for old and new bill");
            }

            if (oldBill == newBill) {
                cashierContext.getBill().forceUpdate();
            } else {
                cashierContext.getBill().setValue(newBill);
            }
        });
    }

    public void setProductBill(ProductBill productBill) {
        this.productBill = productBill;
        this.counter.setValue(this.productBill.getAmount());

        if (this.counter.getValue() == 0) {
            this.decrement.setDisable(true);
        }

        if (this.counter.getValue() == this.productBill.getProduct().getStock()) {
            this.increment.setDisable(true);
        }
    }
}
