package mzc.app.view_model.components.cashier;

import lombok.Getter;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.utils.reactive.State;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.CashierPageViewModel;
import org.controlsfx.control.SearchableComboBox;

public class CustomerSelectorViewModel extends BaseViewModel {
    @Getter
    private SearchableComboBox<Customer> customerSelector = new SearchableComboBox<>();

    @Getter
    private State<Customer> customer = new State<>(null);

    @Override
    public void init() {
        super.init();

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var customer = cashierContext.getCustomer().getValue();
        var guestCustomer = cashierContext.getGuestCustomer().getValue();

        if (customer.getType() != CustomerType.BASIC) {
            throw new RuntimeException("Initial customer should be a basic");
        }

        if (guestCustomer != customer) {
            throw new RuntimeException("Initial customer and guest customer should be equal");
        }

        this.customerSelector.getItems().add(customer);
        this.customerSelector.getItems().addAll(getAdapter().getCustomer().getRegisteredCustomer().stream().filter(cust -> !cust.isDeactivated()).toList());
        this.customerSelector.setValue(customer);
        this.customer.setValue(customer);
//
//        System.out.println("Customer value from customer selector " + this.customerSelector.getValue().getName() + this.customerSelector.getValue().getId());
//        System.out.println("CashierContext - Customer" + cashierContext.getCustomer().getValue().getName() + cashierContext.getCustomer().getValue().getId());
//        System.out.println("CashierContext - Bill Customer" + cashierContext.getBill().getValue().getCustomer().getName() + cashierContext.getBill().getValue().getCustomer().getId());

        cashierContext.getGuestCustomer().addListener((observableValue, prev, next) -> {
            if (next != null) {
                this.customerSelector.getItems().set(0, next);
//                this.customerSelector.getItems().addAll(getAdapter().getCustomer().getRegisteredCustomer());

                if (this.customerSelector.getValue() == prev) {
                    this.customerSelector.setValue(next);
                }
            }
        });

        this.customerSelector.setOnAction(e -> {
            var value = this.customerSelector.valueProperty().getValue();
            if (value != null) {
                if (this.customer.getValue() != value) {
                    this.customer.setValue(value);
                }
            }
        });

        this.customer.addListener((observableValue, old, next) -> {
            if (old != next) {
                System.out.println("CustomerSelector - Setting customer to " + next.getName() + next.getId() + " from " + old.getName() + old.getId());
                cashierContext.getCustomer().setValue(next);
            }
        });

//        cashierContext.getCustomer().addListener((observableValue, customer1, t1) -> {
//            System.out.println("CashierContext - Customer Changed from " + customer1.getName() + customer1.getId() + " to " + t1.getName() + t1.getId());
//            System.out.println("Customer value from customer selector " + this.customerSelector.getValue().getName() + this.customerSelector.getValue().getId());
//        });
//
//        cashierContext.getBill().addListener((observableValue, prev, next) -> {
//            System.out.println("CashierContext - Customer Changed from " + prev.getCustomer().getName() + prev.getCustomer().getId() + " to " + next.getCustomer().getName() + next.getCustomer().getId());
//            System.out.println("Customer value from customer selector " + this.customerSelector.getValue().getName() + this.customerSelector.getValue().getId());
//        });
    }
}
