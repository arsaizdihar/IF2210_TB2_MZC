package mzc.app.view_model.page;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.Product;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.components.FileDialogView;
import mzc.app.view.page.ParamPageView;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Getter @Setter
public class MainPageViewModel extends PageViewModel {
    private final @NotNull Label label = new Label("");
    private final @NotNull Button counterButton = new Button("Counter");
    private final @NotNull Button changePageButton = new Button("Change page");
    private final @NotNull Label counterLabel = new Label("0");
    private final @NotNull TextField textField = new TextField();
    private FileDialogView fileDialogView;
    public MainPageViewModel() {
        super("MZC");
    }

    @Override
    public void init() {
        super.init();
        fileDialogView = new FileDialogView(new Button("Open file"), file -> {
            String absolutePath = file.getAbsolutePath();
            Product product = new Product();
            try {
                product.updateImage(absolutePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        createView(fileDialogView);
        Context<String> textContext = useContext(String.class);
        PageContext pageContext = useContext(PageContext.class).getValue();
        textField.setText(textContext.getValue());
        label.textProperty().bind(textContext);
        textContext.bindBidirectional(textField.textProperty());
        State<Integer> counter = new State<>(0);
        counterButton.setOnAction(event -> {
            counter.setValue(value -> value + 1);
        });
        counterLabel.textProperty().bind(Bindings.createObjectBinding(() -> counter.getValue().toString(), counter));
        changePageButton.setOnAction(event -> {
            ParamPageView page = new ParamPageView("Param Page");
            pageContext.createPage(page);
            pageContext.changePage(page);
        });
    }
}
