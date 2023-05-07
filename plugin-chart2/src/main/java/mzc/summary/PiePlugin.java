package mzc.summary;

import lombok.Getter;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.PageEntry;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.setting.AppSetting;
import mzc.summary.view_model.SummaryView;

public class PiePlugin extends Plugin {
    @Getter
    private static AppSetting appSetting;

    public PiePlugin() {
        super("Pie Chart Plugin");
    }

    @Override
    public void setup(App appContext) {
        appSetting = appContext.getAppSetting();

        var customerEntry = new PageEntry("Summary", "summary", SummaryView.class);
        appContext.getPages().put(customerEntry.getKey(), customerEntry);
    }

    @Override
    public void postSetup() {
    }
}
