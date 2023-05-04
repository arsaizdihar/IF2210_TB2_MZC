package mzc.app.view_model.components.cashier;

import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.view.components.cashier.CustomerSelectorView;
import mzc.app.view_model.components.split_page.RightSideViewModel;

public class PaymentSummaryViewModel extends RightSideViewModel {
    @Getter
    private CustomerSelectorView customerSelector = new CustomerSelectorView();

    @Getter
    private VBox container = new VBox();

    @Override
    public void init() {
        super.init();
        createView(this.customerSelector);
    }
}
