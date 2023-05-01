package mzc.app.bootstrap;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import mzc.app.view.base.PageView;
import mzc.app.view.page.MainPageView;
import mzc.app.view_model.base.PageViewModel;

import java.util.HashMap;
import java.util.Map;

public class App {
    @Getter
    protected @NonNull AppSetting appSetting;

    @Getter
    protected @NonNull Map<String, Class<? extends PageView<? extends PageViewModel>>> pages;

    public App() {
        // initialize app setting
        this.appSetting = AppSettingManager.get();

        // bootstrap page list
        this.pages = new HashMap<>();
        this.pages.put("default", MainPageView.class);
    }

    public <T extends PageView<? extends PageViewModel>> void addPage(String key, Class<T> pageClass) throws Exception {
        // try to create class
        pageClass.getConstructor().newInstance();

        // put
        this.pages.put(key, pageClass);
    }
}
