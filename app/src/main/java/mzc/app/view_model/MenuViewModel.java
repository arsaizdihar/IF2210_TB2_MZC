package mzc.app.view_model;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.view.TabsView;
import mzc.app.view_model.base.BaseViewModel;

@Getter
public class MenuViewModel extends BaseViewModel {
    private final VBox root = new VBox();
    private TabsView tabsView;
    private MenuBar menuBar = new MenuBar();

    @Override
    public void init() {
        var customer = getAdapter().getCustomer().getById(1L);
        tabsView = createView(TabsView.class);
        App app = AppManager.get();

        var pages = app.getPages().values();
        var menu = new Menu("Page");

        for (var page : pages) {
            MenuItem menuItem = new MenuItem(page.getTitle());
            menuItem.setOnAction(e -> {
                tabsView.getViewModel().addNewTab(page);
            });
            menu.getItems().add(menuItem);
        }
        menuBar.getMenus().add(menu);
    }

}
