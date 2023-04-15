package mzc.app.view_model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainViewModel extends BaseViewModel {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}