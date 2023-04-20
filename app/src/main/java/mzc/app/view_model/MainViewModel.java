package mzc.app.view_model;

import javafx.scene.control.Label;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

@Getter
public class MainViewModel extends BaseViewModel {
    private final @NotNull Label welcomeText;

    public MainViewModel() {
        welcomeText = new Label("");
    }

    public void onHelloButtonClick() {
        getAdapter().getCustomer().getById(1L);
        welcomeText.setText("Hello World!");
    }
}