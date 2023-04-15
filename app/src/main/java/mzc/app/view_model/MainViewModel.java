package mzc.app.view_model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mzc.app.model.Member;

public class MainViewModel extends BaseViewModel {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Member member = this.getAdapter().getMember().getById(1L);
        welcomeText.setText(member.getName());
    }
}