package mzc.app.view;

import javafx.scene.Node;
import javafx.scene.Parent;
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
        VBox root = getViewModel().getRoot();
        StackPane stackPane = new StackPane();
        stackPane.getProperties().put("VBox.vgrow", "ALWAYS");
        stackPane.getChildren().addAll(getViewModel().getHomeView().getView(), getViewModel().getTabsView().getView());
        root.getChildren().addAll(getViewModel().getMenuBar(), stackPane);
        return getViewModel().getRoot();
    }
}
