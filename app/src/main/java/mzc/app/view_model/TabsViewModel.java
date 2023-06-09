package mzc.app.view_model;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;
import mzc.app.bootstrap.PageEntry;
import mzc.app.view.base.PageView;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
public class TabsViewModel extends BaseViewModel {
    private TabPane tabPane = new TabPane();

    private void setupTab(PageTab pageTab) {
        pageTab.getTab().setOnClosed(e -> {
            if (tabPane.getTabs().size() == 0) {
                tabPane.setStyle("-fx-background-color: transparent;");
            }
        });
        tabPane.getTabs().add(pageTab.getTab());
        tabPane.getSelectionModel().select(pageTab.getTab());
    }

    public void addNewTab(PageEntry entry) {
        tabPane.setStyle("-fx-background-color: white;");
        PageTab pageTab = new PageTab(this, entry);
        setupTab(pageTab);
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
                    addContext(view);
                    return tabs.createView(view);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public <T extends PageView<?>> T createPage(T page) {
                addContext(page);
                return tabs.createView(page);
            }
        });

        public PageTab(@NotNull TabsViewModel tabs, PageEntry entry) {
            this.tabs = tabs;
            currentPage = pageContext.createPage(entry.getPageClass());
            tab = new Tab();
            setupListeners(currentPage);
            tab.textProperty().bind(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
        }

        public void changePage(PageView<?> page) {
            if (page.getViewModel().getParentView() != currentPage.getViewModel().getParentView()) {
                throw new RuntimeException("Cannot change page to a page with different parent view");
            }
            currentPage = page;
            addContext(currentPage);
            setupListeners(currentPage);
            tab.textProperty().bind(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
        }

        void setupListeners(PageView<?> pageView) {
            var newTab = this.tab;
            this.tab.setOnCloseRequest(e -> {
                pageView.getViewModel().onTabClose();
            });
            tab.setOnSelectionChanged(e -> {
                if (newTab.isSelected()) {
                    pageView.getViewModel().onTabFocus();
                }
            });
        }

        void addContext(PageView<?> pageView) {
            pageView.getViewModel().addPageContext(this.pageContext);

        }
    }
}
