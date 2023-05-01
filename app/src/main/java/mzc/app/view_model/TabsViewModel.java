package mzc.app.view_model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view.base.PageView;
import mzc.app.view.page.MainPageView;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class TabsViewModel extends BaseViewModel {
    private TabPane tabPane = new TabPane();

    public void init() {
        System.out.println("TabsViewModel init");
        Tab newTab = new Tab("+");
        newTab.getStyleClass().add("add-tab");
        newTab.setOnSelectionChanged(event -> {
            if (newTab.isSelected()) {
                addNewTab();
            }
        });
        tabPane.getTabs().add(newTab);
    }

    private void addNewTab() {
        PageTab pageTab = new PageTab(this);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, pageTab.getTab());
        tabPane.getSelectionModel().select(pageTab.getTab());
    }

    public static class PageTab {
        private final @NotNull ChangeListener<String> updateTitle = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tab.setText(newValue);
            }
        };
        private @NotNull PageView<?> currentPage;

        @Getter
        private final @NotNull Tab tab;
        private final @NotNull TabsViewModel tabs;

        public PageTab(@NotNull TabsViewModel tabs) {
            this.tabs = tabs;
            currentPage = tabs.createView(MainPageView.class);
            currentPage.getViewModel().addTitleListener(updateTitle);
            currentPage.getViewModel().setOnChangePage(this::changePage);
            tab = new Tab(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
        }

        public void changePage(PageView<?> page) {
            if (page.getViewModel().getParentView() != currentPage.getViewModel().getParentView()) {
                throw new RuntimeException("Cannot change page to a page with different parent view");
            }
            currentPage.getViewModel().removeTitleListener(updateTitle);
            currentPage = page;
            currentPage.getViewModel().addTitleListener(updateTitle);
            currentPage.getViewModel().setOnChangePage(this::changePage);
            tab.setText(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
            currentPage.getViewModel().addTitleListener(updateTitle);
        }

        public <T extends PageView<?>> T createPage(Class<T> pageClass) {
            return tabs.createView(pageClass);
        }
    }
}
