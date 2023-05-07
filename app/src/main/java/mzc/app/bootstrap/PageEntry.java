package mzc.app.bootstrap;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import mzc.app.view.base.PageView;
import mzc.app.view_model.base.PageViewModel;

public class PageEntry {
    public PageEntry(@NonNull String title, @NonNull String key, @NonNull Class<? extends PageView<? extends PageViewModel>> pageClass) {
        this.title = title;
        this.key = key;
        this.pageClass = pageClass;
    }


    @Getter
    protected @NonNull String title;

    @Getter
    protected @NonNull String key;

    @Getter
    @Setter
    protected @NonNull Class<? extends PageView<? extends PageViewModel>> pageClass;
}
