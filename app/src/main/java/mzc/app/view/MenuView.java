package mzc.app.view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import mzc.app.annotation.ModelInject;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.MenuViewModel;
import org.jetbrains.annotations.NotNull;

@ModelInject(MenuViewModel.class)
public class MenuView extends BaseView<MenuViewModel> {
    public MenuView() {
        super();
    }

    @Override
    public @NotNull Parent getView() {
        BorderPane root = getViewModel().getRoot();
        root.setTop(getViewModel().getMenuBar());
        StackPane stackPane = new StackPane();
        VBox.setVgrow(stackPane, Priority.ALWAYS);
        var tabs = getViewModel().getTabsView().getView();
        stackPane.getChildren().addAll(getViewModel().getHomeView().getView(), tabs);
        root.setCenter(stackPane);
        return root;
    }
}
