package mzc.app.view_model.components.cashier;

import lombok.Getter;
import mzc.app.model.Customer;
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
        var customer = cashierContext.getBill().getValue().getCustomer();
        customer.setName("Bukan Anggota");
        this.customerSelector.getItems().add(customer);
        this.customerSelector.getItems().addAll(getAdapter().getCustomer().getRegisteredCustomer());
        this.customer.setValue(customer);

        this.customer.bindBidirectional(this.customerSelector.valueProperty());
        this.customerSelector.setValue(this.customer.getValue());

        this.customer.addListener((observableValue, old, next) -> {
            if (next == null) {
                cashierContext.loadBill(customer);
            } else {
                cashierContext.loadBill(next);
            }
        });
    }
}
