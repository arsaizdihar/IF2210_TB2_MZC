package mzc.app.view_model.components.ui;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

@Getter
public class TextInputViewModel extends BaseViewModel {
    @Getter
    @NotNull Label label = new Label();

    @Getter
    @NotNull TextField textField = new TextField();

    @Getter
    @NotNull VBox main = new VBox();
    public void init() {
        this.main.getChildren().add(this.label);
        this.main.getChildren().add(this.textField);
    }

    public void setter(String str, int width) {
        this.label.setText(str);
        this.textField.setPromptText(str);
        this.textField.setPrefWidth(width);
    }
}
