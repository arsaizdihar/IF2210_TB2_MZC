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
        this.customerSelector.getItems().addAll(getAdapter().getCustomer().getRegisteredCustomer());

        cashierContext.getGuestCustomer().addListener((observableValue, prev, next) -> {
            if (next != null) {
                this.customerSelector.getItems().add(next);
                this.customerSelector.getItems().set(0, next);
                this.customerSelector.getItems().addAll(getAdapter().getCustomer().getRegisteredCustomer());
                this.customerSelector.setValue(next);
            }
        });

        this.customer.setValue(customer);
        this.customerSelector.setValue(customer);

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
                cashierContext.getCustomer().setValue(next);
            }
        });
    }
}
