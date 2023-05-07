package mzc.plugin_charge;

import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
import mzc.app.adapter.sql.Schema;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.PageEntry;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_charge.adapter.ChargeManager;
import mzc.plugin_charge.view.CashierPageView;
import mzc.plugin_charge.view.ChargeSettingView;

public class ChargePlugin extends Plugin {
    @Getter
    protected static AppSetting appSetting;

    public ChargePlugin() {
        super("Charge Plugin");
    }

    @Override
    public void setup(App appContext) {
        appSetting = appContext.getAppSetting();

        if (appSetting.getStorageMethod() == AdapterType.SQLRaw) {
            Schema.setValue(Schema.getValue() + " " + mzc.plugin_charge.adapter.sql.Schema.getValue());
        }

        var pageEntry = new PageEntry("Kasir", "cashier", CashierPageView.class);

        appContext.getPages().put("cashier", pageEntry);

        appContext.getSettingTabs().put("Charges", ChargeSettingView.class);
    }

    @Override
    public void postSetup() {
        var adapter = ChargeManager.getAdapter();

        if (adapter.getAll().size() == 0) {
            for (var charge : ChargeManager.getChargeSeed()) {
                adapter.persist(charge);
            }
        }
    }
}
