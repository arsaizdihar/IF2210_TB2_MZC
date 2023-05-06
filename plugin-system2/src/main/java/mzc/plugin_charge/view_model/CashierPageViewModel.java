package mzc.plugin_charge.view_model;

import mzc.app.utils.reactive.Context;
import mzc.app.view.components.cashier.LeftSideCashierView;
import mzc.plugin_charge.view.PaymentSummaryView;

public class CashierPageViewModel extends mzc.app.view_model.page.CashierPageViewModel {
    @Override
    public void init() {
        Context<CashierContext> cashierContext = new Context<>(new CashierContext(getAdapter()));
        addContext(cashierContext);

        var context = cashierContext.getValue();

        // listen to customer change, if customer change, load bill
        context.getCustomer().addListener((observableValue, prev, customer) -> {
            if (customer != null) {
                context.loadBill(customer);
            }
        });

        context.loadBill();

        this.setLeft(new LeftSideCashierView());
        this.setRight(new PaymentSummaryView());
    }
}
