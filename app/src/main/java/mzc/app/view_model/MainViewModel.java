package mzc.app.view_model;

import javafx.scene.control.Label;
import lombok.Getter;
import mzc.app.model.Customer;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

@Getter
public class MainViewModel extends BaseViewModel {
    private final @NotNull Label welcomeText;

    public MainViewModel() {
        welcomeText = new Label("");
    }

    public void onHelloButtonClick() {
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