package mzc.app.bootstrap;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import mzc.app.view.base.PageView;
import mzc.app.view_model.base.PageViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageEntry {
    public PageEntry(@NonNull String title, @NonNull String key, @NonNull Class<? extends PageView<? extends PageViewModel>> pageClass, @NonNull List<ViewEntry> subentries) {
        this.title = title;
        this.key = key;
        this.pageClass = pageClass;

        this.subentries = new HashMap<>();

        for (var entry : subentries) {
            this.subentries.put(entry.getKey(), entry);
        }
    }

    public PageEntry(@NonNull String title, @NonNull String key, @NonNull Class<? extends PageView<? extends PageViewModel>> pageClass) {
        this.title = title;
        this.key = key;
        this.pageClass = pageClass;
        this.subentries = new HashMap<>();
    }

    @Getter
    protected @NonNull String title;

    @Getter
    protected @NonNull String key;

    @Getter
    @Setter
    protected @NonNull Class<? extends PageView<? extends PageViewModel>> pageClass;

    @Getter
    @Setter
    protected @NonNull Map<String, ViewEntry> subentries;


}
