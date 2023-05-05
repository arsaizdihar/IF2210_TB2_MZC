package mzc.app.view_model.page;

import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.bootstrap.AppManager;
import mzc.app.view.components.settings.SettingsTabView;
import mzc.app.view_model.base.PageViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class SettingsPageViewModel extends PageViewModel {
    @NotNull @Getter @Setter
    private List<Class<? extends SettingsTabView>> tabViews = new ArrayList<>();
    @NotNull @Getter
    private TabPane paneMode = new TabPane();
    public SettingsPageViewModel() {
        super("Settings Page");
    }

    @Override
    public void init() {
        super.init();
        AppManager.get().getSettingTabs().entrySet().forEach(stringClassEntry -> {
            var tab = new Tab(stringClassEntry.getKey());
            tab.setClosable(false);
            tab.setOnSelectionChanged(e -> {
                if (tab.isSelected()) {
                    System.out.println("Selected");
                    var view = createView(stringClassEntry.getValue());
                    tab.setContent(view.getView());
                }
            });
            paneMode.getTabs().add(tab);
            tabViews.add(stringClassEntry.getValue());
        });
    }

    @Override
    public void onTabFocus() {

    }

    @Override
    public void onTabClose() {

    }
}
