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
        welcomeText.setText("Hello World!");
    }
}