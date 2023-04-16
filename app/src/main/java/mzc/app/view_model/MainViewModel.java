package mzc.app.view_model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mzc.app.model.Customer;
import mzc.app.model.Product;

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
        Product newProduct = new Product(0, "test", 1000, 500, "Test", "test");
        getAdapter().getCustomer().persist(newCustomer);
        getAdapter().getProduct().persist(newProduct);

    }
}