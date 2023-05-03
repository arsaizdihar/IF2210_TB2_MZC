package mzc.app.view_model.components.cashier;

import javafx.scene.control.Button;
import lombok.Getter;
import mzc.app.model.ProductBill;
import mzc.app.utils.reactive.State;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

public class ProductViewModel extends BaseViewModel {
    @Getter
    private ProductBill productBill;

    @Getter
    private State<Integer> counter = new State<Integer>(0);

    @Getter
    private Button increment = new Button();

    @Getter
    private Button decrement = new Button();

    @Override
    public void init() {
        super.init();

        this.increment.setOnAction(e -> {
            counter.setValue(counter.getValue() + 1);
            getAdapter().getProductBill().persist(this.productBill);
            useContext(CashierPageViewModel.CashierContext.class).getValue().getBill().forceUpdate();
        });

        this.decrement.setOnAction(e -> {
            if (counter.getValue() > 0) {
                counter.setValue(counter.getValue() - 1);
                getAdapter().getProductBill().persist(this.productBill);
                useContext(CashierPageViewModel.CashierContext.class).getValue().getBill().forceUpdate();
            }
        });

        this.counter.addListener((observableValue, prev, next) -> {
            if (next == 0) {
                this.decrement.setDisable(true);
            }

            if (next == this.productBill.getProduct().getStock()) {
                this.increment.setDisable(true);
            }

            if (next >= 0 && this.decrement.isDisabled()) {
                this.decrement.setDisable(false);
            }

            if (next < this.productBill.getProduct().getStock() && this.increment.isDisabled()) {
                this.increment.setDisable(false);
            }
        });
    }

    public void setProductBill(ProductBill productBill) {
        this.productBill = productBill;
        this.counter.setValue(this.productBill.getAmount());
    }
}
