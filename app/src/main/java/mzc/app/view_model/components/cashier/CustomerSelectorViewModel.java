package mzc.app.view_model.components.cashier;

import lombok.Getter;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.ui.FormGroupView;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.page.CashierPageViewModel;
import org.controlsfx.control.SearchableComboBox;

public class CustomerSelectorViewModel extends BaseViewModel {
    @Getter
    private SearchableComboBox<Customer> customerSelector = new SearchableComboBox<>();

    @Getter
    private State<Customer> customer = new State<>(null);

    @Getter
    private FormGroupView container = new FormGroupView(customerSelector);

    @Override
    public void init() {
        super.init();
        createView(container);
        container.setLabel("Pilih Pengguna");

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

        cashierContext.getGuestCustomer().addListener((observableValue, prev, next) -> {
            if (next != null) {
                this.customerSelector.getItems().set(0, next);
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
    }
}
