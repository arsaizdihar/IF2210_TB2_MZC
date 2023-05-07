package mzc.app.view_model;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.bootstrap.PageEntry;
import mzc.app.view.HomeView;
import mzc.app.view.TabsView;
import mzc.app.view_model.base.BaseViewModel;

import java.util.Comparator;

@Getter
public class MenuViewModel extends BaseViewModel {
    private final BorderPane root = new BorderPane();
    private TabsView tabsView;
    private final MenuBar menuBar = new MenuBar();
    private HomeView homeView;

    @Override
    public void init() {
        tabsView = createView(TabsView.class);
        homeView = createView(HomeView.class);
        App app = AppManager.get();

        var pages = app.getPages().values().stream().sorted(Comparator.comparing(PageEntry::getTitle)).toList();
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
