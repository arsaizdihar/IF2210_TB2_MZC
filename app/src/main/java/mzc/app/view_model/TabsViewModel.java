package mzc.app.view_model;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view.base.PageView;
import mzc.app.view.page.MainPageView;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@Getter @Setter
public class TabsViewModel extends BaseViewModel {
    private TabPane tabPane = new TabPane();

    public void init() {
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
        private @NotNull PageView<?> currentPage;

        @Getter
        private final @NotNull Tab tab;
        private final @NotNull TabsViewModel tabs;
        private final PageViewModel.PageContext pageContext = new PageViewModel.PageContext(this::changePage, new PageViewModel.CreatePageFunc() {
            @Override
            public <T extends PageView<?>> T createPage(Class<T> pageClass) {
                try {
                    T view = pageClass.getDeclaredConstructor().newInstance();
                    addPageContext(view);
                    return tabs.createView(view);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public <T extends PageView<?>> T createPage(T page) {
                addPageContext(page);
                return tabs.createView(page);
            }
        });

        public PageTab(@NotNull TabsViewModel tabs) {
            this.tabs = tabs;
            currentPage = new MainPageView();
            addPageContext(currentPage);
            tabs.createView(currentPage);
            tab = new Tab();
            tab.textProperty().bind(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
        }

        public void changePage(PageView<?> page) {
            if (page.getViewModel().getParentView() != currentPage.getViewModel().getParentView()) {
                throw new RuntimeException("Cannot change page to a page with different parent view");
            }
            currentPage = page;
            addPageContext(currentPage);
            tab.textProperty().bind(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
        }

        void addPageContext(PageView<?> pageView) {
            pageView.getViewModel().addPageContext(this.pageContext);
        }
    }
}
