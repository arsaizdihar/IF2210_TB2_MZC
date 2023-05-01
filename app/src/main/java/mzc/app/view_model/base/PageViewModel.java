package mzc.app.view_model.base;

import lombok.Setter;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.base.PageView;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class PageViewModel extends BaseViewModel {
    public static class TitleContext extends State<String> {
        public TitleContext(String value) {
            super(value);
        }
    }

    public interface CreatePageFunc {
        <T extends PageView<? extends PageViewModel>> T createPage(Class<T> pageClass);
        <T extends PageView<? extends PageViewModel>> T createPage(T page);
    }

    public static class PageContext {
        @NotNull Consumer<PageView<?>> onPageChange;
        @NotNull CreatePageFunc _createPage;

        public PageContext(@NotNull Consumer<PageView<?>> onPageChange, @NotNull CreatePageFunc createPage) {
            this.onPageChange = onPageChange;
            this._createPage = createPage;
        }

        public <T extends PageView<?>> void changePage(T page) {
            onPageChange.accept(page);
        }

        public <T extends PageView<? extends PageViewModel>> T createPage(Class<T> pageClass) {
            return _createPage.createPage(pageClass);
        }

        public <T extends PageView<? extends PageViewModel>> T createPage(T page) {
            return _createPage.createPage(page);
        }
    }

    private final @NotNull Context<TitleContext> title;

    @Override
    protected void setParentView(BaseViewModel parentView) {
        super.setParentView(parentView);
    }

    public PageViewModel(String title) {
        this.title  = new Context<>(new TitleContext(title));
    }

    public void addPageContext(PageContext pageContext) {
        Context<PageContext> context = new Context<>(pageContext);
        this.addContext(context);
    }

    public void setTitle(String title) {
        this.title.getValue().setValue(title);
    }

    public TitleContext getTitle() {
        return title.getValue();
    }
}
