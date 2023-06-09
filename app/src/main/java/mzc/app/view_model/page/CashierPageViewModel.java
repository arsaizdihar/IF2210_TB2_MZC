package mzc.app.view_model.page;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.cashier.LeftSideCashierView;
import mzc.app.view.components.cashier.PaymentSummaryView;

public class CashierPageViewModel extends SplitPageViewModel {
    private boolean initialLoad = true;

    public CashierPageViewModel() {
        super("Cashier");
    }

    public static class CashierContext {
        @Getter
        private final State<Bill> bill = new State<>(null);

        @Getter
        private State<Customer> customer = new State<>(null);

        @Getter
        private State<Customer> guestCustomer = new State<>(null);

        private final IMainAdapter adapter;

        public CashierContext(IMainAdapter adapter) {
            this.adapter = adapter;
        }

        /**
         * Load bill with new basic customer and
         */
        public void loadBill() {
            System.out.println("CashierPageViewModel - Loading Bill");

            Bill newBill = new Bill();
            Customer customer = new Customer();
            customer.setName("Bukan Anggota");
            this.adapter.getCustomer().persist(customer);
            this.getGuestCustomer().setValue(customer);
            newBill.setCustomer(customer);
            this.adapter.getBill().persist(newBill);
            // setting customer here will cause customer change listener to call loadBill(customer)
            this.getCustomer().setValue(customer);
//            this.getBill().setValue(newBill);
        }

        /**
         * Load bill with existing customer
         *
         * @param customer customer to load bill
         */
        public void loadBill(Customer customer) {
            System.out.println("CashierPageViewModel - Loading Bill for Customer " + customer.getName());
            var first = this.adapter.getBill().getByCustomerId(customer.getId()).stream().findFirst();

            if (first.isEmpty()) {
                // if customer dont have bill, create new bill
                if (this.bill.getValue() == null || this.bill.getValue().getCustomerId() != customer.getId()) {
                    System.out.println("Customer don't have bill. Creating a new one");
                    Bill newBill = new Bill(customer);
                    this.adapter.getBill().persist(newBill);
                    this.bill.setValue(newBill);
                }
            } else {
                System.out.println("Loading current customer bill");
                // if customer have bill, load it
                first.get().setCustomer(adapter.getCustomer().getById(first.get().getCustomerId()));

                boolean equal = first.get() == this.bill.getValue();

                this.bill.setValue(first.get());

                if (equal) {
                    this.bill.forceUpdate();
                }
            }
        }
    }

    @Override
    public void init() {
        super.init();
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

    @Override
    public void onTabFocus() {
        if (initialLoad) {
            initialLoad = false;
        } else {
            var context = useContext(CashierContext.class).getValue();
            context.loadBill(context.getBill().getValue().getCustomer());
        }
    }

    @Override
    public void onTabClose() {
        var context = useContext(CashierContext.class).getValue();

        var bill = context.getBill().getValue();
        var customer = bill.getCustomer();

        // if customer is basic, delete bill and customer
        if (customer.getType() == CustomerType.BASIC) {
            var productBills = getAdapter().getBill().getProducts(bill);

            productBills.forEach(productBill -> {
                getAdapter().getProductBill().delete(productBill);
            });

            getAdapter().getBill().delete(bill);
            getAdapter().getCustomer().delete(customer);
        }
    }
}
