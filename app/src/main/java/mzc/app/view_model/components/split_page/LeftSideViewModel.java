package mzc.app.view_model.components.split_page;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.utils.reactive.State;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeftSideViewModel extends BaseViewModel {
    @Getter
    private final @NotNull Label titleLabel;

    @Getter
    final @NotNull VBox vBox = new VBox();
    @Getter
    final @NotNull HBox hBox = new HBox();

    @Getter
    final @NotNull ScrollPane scrollPane = new ScrollPane();

    @Getter
    final @NotNull VBox container = new VBox();

    @Getter
    protected State<List<BaseView<?>>> children = new State<>(new ArrayList<>());


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


    public void setOnButtonClicked(EventHandler<ActionEvent> handler) {
        if (button == null) {
            throw new RuntimeException("Must set button first");
        }
        button.setOnAction(handler);
    }

    @Override
    public void init() {
        super.init();
        scrollPane.setContent(container);
        children.addListener((o, b, newChildren) -> {
            container.getChildren().setAll(newChildren.stream().map(BaseView::getView).toList());
        });
    }
}
