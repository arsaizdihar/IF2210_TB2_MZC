package mzc.app.view_model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mzc.app.model.Customer;

public class MainViewModel extends BaseViewModel {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Customer customer = this.getAdapter().getCustomer().getById(1L);
        if (customer != null) {
            welcomeText.setText(customer.getName());
            System.out.println(this.getAdapter().getCustomer().getRegisteredCustomer());
            System.out.println(this.getAdapter().getCustomer().getBills(customer));
        }

        Customer newCustomer = new Customer("Ken", "2321");
        getAdapter().getCustomer().persist(newCustomer);

    }
}