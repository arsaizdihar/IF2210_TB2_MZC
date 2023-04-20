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

    public TabsViewModel() {
        Tab newTab = new Tab("+");
        newTab.setOnSelectionChanged(event -> {
            if (newTab.isSelected()) {
                addNewTab();
            }
        });
        tabPane.getTabs().add(newTab);
    }

    private void addNewTab() {
        PageTab pageTab = new PageTab();
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

        public PageTab() {
            currentPage = new MainPageView();
            currentPage.getViewModel().addTitleListener(updateTitle);
            currentPage.getViewModel().setOnChangePage(this::changePage);
            tab = new Tab(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
        }

        public void changePage(PageView<?> page) {
            currentPage.getViewModel().removeTitleListener(updateTitle);
            currentPage = page;
            currentPage.getViewModel().addTitleListener(updateTitle);
            currentPage.getViewModel().setOnChangePage(this::changePage);
            tab.setText(currentPage.getViewModel().getTitle());
            tab.setContent(currentPage.getView());
            currentPage.getViewModel().addTitleListener(updateTitle);
        }
    }
}
