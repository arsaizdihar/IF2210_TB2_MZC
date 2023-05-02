package mzc.app.view_model.components.split_page;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public class LeftSideViewModel extends BaseViewModel {
    @Getter
    private final @NotNull Label titleLabel;

    @Getter final @NotNull VBox vBox = new VBox();
    @Getter final @NotNull HBox hBox = new HBox();

    @Getter
    private Button button;

    public LeftSideViewModel() {
        titleLabel = new Label();
    }

    public void setButtonText(String text) {
        if (button == null) {
            button = new Button(text);
        } else {
            button.setText(text);
        }
    }
}
