package mzc.app.bootstrap;

import lombok.Getter;
import lombok.Setter;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.base.BaseViewModel;
import org.jetbrains.annotations.NotNull;

public class ViewEntry {
    @Getter
    protected @NotNull String title;

    @Getter
    protected @NotNull String key;

    @Getter
    @Setter
    protected @NotNull Class<? extends BaseView<? extends BaseViewModel>> viewClass;

    public ViewEntry(@NotNull String title, @NotNull String key, @NotNull Class<? extends BaseView<? extends BaseViewModel>> viewClass) {
        this.title = title;
        this.key = key;
        this.viewClass = viewClass;
    }
}
