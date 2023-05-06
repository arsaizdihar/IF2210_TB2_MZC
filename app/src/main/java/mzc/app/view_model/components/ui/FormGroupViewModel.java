package mzc.app.view_model.components.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public class FormGroupViewModel extends BaseViewModel {
    @Getter @Setter
    protected Node input;

    @Getter
    @NotNull Label label = new Label();

    @Getter
    @NotNull VBox main = new VBox();

    public void init() {
        this.main.getChildren().add(this.label);
        this.main.getChildren().add(this.input);
    }
}
