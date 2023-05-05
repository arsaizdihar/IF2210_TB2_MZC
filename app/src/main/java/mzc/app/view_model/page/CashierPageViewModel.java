package mzc.app.view_model.page;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.cashier.LeftSideCashierView;
import mzc.app.view.components.cashier.PaymentSummaryView;

public class CashierPageViewModel extends SplitPageViewModel {
    public CashierPageViewModel() {
        super("Cashier");
    }

    public static class CashierContext {
        @Getter
        private final State<Bill> bill = new State<>(null);

        @Getter
        private final State<Boolean> shouldUpdateSummary = new State<>(true);

        private final IMainAdapter adapter;

        public CashierContext(IMainAdapter adapter) {
            this.adapter = adapter;
        }

        public void loadBill() {
            Bill newBill = new Bill();
            Customer customer = new Customer();
            this.adapter.getCustomer().persist(customer);
            newBill.setCustomer(customer);
            this.adapter.getBill().persist(newBill);
            this.bill.setValue(newBill);

            this.shouldUpdateSummary.setValue(true);
        }

        public void loadBill(Customer customer) {
            var first = this.adapter.getBill().getByCustomerId(customer.getId()).stream().findFirst();

            if (first.isEmpty()) {
                if (this.bill.getValue() == null || this.bill.getValue().getCustomerId() != customer.getId()) {
                    Bill newBill = new Bill(customer);
                    this.adapter.getBill().persist(newBill);
                    this.bill.setValue(newBill);
                    this.shouldUpdateSummary.setValue(true);
                }
            } else {
                this.bill.setValue(first.get());
                this.shouldUpdateSummary.setValue(true);
            }
        }
    }

    @Override
    public void init() {
        super.init();
        Context<CashierContext> cashierContext = new Context<>(new CashierContext(getAdapter()));
        addContext(cashierContext);
        cashierContext.getValue().loadBill();

        this.setLeft(new LeftSideCashierView());
        this.setRight(new PaymentSummaryView());
    }
}
