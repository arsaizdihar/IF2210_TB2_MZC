package mzc.app.view_model.page;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class MainPageViewModel extends PageViewModel {
    private final @NotNull Label label = new Label("");
    private final @NotNull Button counterButton = new Button("Counter");
    private final @NotNull Label counterLabel = new Label("0");
    private final @NotNull TextField textField = new TextField();
    private final State<Integer> counter = new State<>(0);
    public MainPageViewModel() {
        super("MZC");
    }

    @Override
    public void init() {
        super.init();
        Context<String> textContext = useContext(String.class);
        textField.setText(textContext.getValue());
        label.setText(textContext.getValue());
        textContext.addListener((observable, oldValue, newValue) -> {
            label.setText(newValue);
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textContext.setValue(newValue);
        });
        counterButton.setOnAction(event -> {
            counter.setValue(value -> value + 1);
        });

        counter.addListener((observable, oldValue, newValue) -> {
            counterLabel.setText(newValue.toString());
        });
    }
}
