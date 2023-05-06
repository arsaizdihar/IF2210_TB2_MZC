package mzc.plugin_charge.view_model;

import mzc.plugin_charge.view.SubtotalView;

public class PaymentSummaryViewModel extends mzc.app.view_model.components.cashier.PaymentSummaryViewModel {
    public PaymentSummaryViewModel() {
        super();
        this.totalView = new SubtotalView();
    }
}
