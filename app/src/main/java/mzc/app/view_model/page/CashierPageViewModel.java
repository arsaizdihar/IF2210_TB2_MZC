package mzc.app.view_model.page;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.ProductBill;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.cashier.LeftSideCashierView;
import mzc.app.view.components.cashier.PaymentSummaryView;

import java.util.ArrayList;
import java.util.List;

public class CashierPageViewModel extends SplitPageViewModel {
    public CashierPageViewModel() {
        super("Cashier");
    }

    public static class CashierContext {
        @Getter
        private final State<List<ProductBill>> items = new State<>(new ArrayList<>());

        @Getter
        private final State<Bill> bill = new State<>(null);

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

            this.getItems().setValue(new ArrayList<>());
        }

        public void loadBill(Customer customer) {
            var first = customer.getBills().stream().findFirst();

            if (first.isEmpty()) {
                if (this.bill.getValue() == null || this.bill.getValue().getCustomerId() != customer.getId()) {
                    Bill newBill = new Bill(customer);
                    this.adapter.getBill().persist(newBill);
                    this.bill.setValue(newBill);
                }
            } else {
                this.bill.setValue(first.get());
            }

            this.items.setValue(new ArrayList<>());
            this.bill.getValue().getProducts().forEach(productBill -> {
                this.items.getValue().add(productBill);
            });
        }
    }

    @Override
    public void init() {
        super.init();
        Context<CashierContext> cashierContext = new Context<>(new CashierContext(getAdapter()));
        addContext(cashierContext);

        this.setLeft(new LeftSideCashierView());
        this.setRight(new PaymentSummaryView());


        // di kiri
//        cashierContext.getValue().getItems().getValue().add();
//        cashierContext.getValue().getItems().forceUpdate();

        // di kanan
//        cashierContext.getValue().getItems().addListener((observableValue, products, t1) -> );
    }
}
