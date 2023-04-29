package mzc.app.view;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.annotation.ModelInject;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.MainViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MainViewModel.class)
@Getter
public class MainView extends BaseView<MainViewModel> {
    private final @NotNull VBox root;

    public MainView() {
        super();
        this.root = new VBox();
        TabsView tabs = getViewModel().createView(TabsView.class);
        root.getChildren().add(tabs.getView());
    }

    @Override
    public @NotNull Parent getView() {
        return root;
    }
}
