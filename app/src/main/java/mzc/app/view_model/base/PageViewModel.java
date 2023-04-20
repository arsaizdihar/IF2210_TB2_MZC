package mzc.app.view_model.base;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import lombok.Getter;
import lombok.Setter;
import mzc.app.view.base.PageView;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class PageViewModel extends BaseViewModel {
    private final @NotNull SimpleStringProperty title;

    @Setter
    private Consumer<PageView<?>> onChangePage;

    public PageViewModel(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return title.get();
    }

    public void addTitleListener(ChangeListener<String> listener) {
        title.addListener(listener);
    }

    public void removeTitleListener(ChangeListener<String> listener) {
        title.removeListener(listener);
    }

    public void changePage(PageView<?> page) {
        if (onChangePage != null)
            onChangePage.accept(page);
    }
}
