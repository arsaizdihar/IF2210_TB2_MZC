package mzc.app.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.annotation.ModelInject;
import mzc.app.view_model.MainViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MainViewModel.class)
@Getter
public class MainView extends BaseView<MainViewModel>{
    private final @NotNull VBox root;
    private final @NotNull Button button;

    public MainView() {
        super();
        this.root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        button = new Button("Hello");
        button.getStyleClass().addAll("btn", "btn-primary");
        button.setOnAction(event -> getViewModel().onHelloButtonClick());
        root.getChildren().addAll(
                getViewModel().getWelcomeText(),
                button
        );
    }

    @Override
    public @NotNull Parent getView() {
        return root;
    }
}
