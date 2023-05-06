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

public class ChargePlugin extends Plugin {
    @Getter
    protected static AppSetting appSetting;

    public ChargePlugin() {
        super("Charge Plugin");
    }

    @Override
    public void setup(App appContext) {
        appSetting = appContext.getAppSetting();

        if (appSetting.getStorageMethod() == AdapterType.SQLORM) {
            throw new RuntimeException("ORM Datastore is not supported");
        } else if (appSetting.getStorageMethod() == AdapterType.SQLRaw) {
            Schema.setValue(Schema.getValue() + " " + mzc.plugin_charge.adapter.sql.Schema.getValue());
        }

        var pageEntry = new PageEntry("Kasir", "cashier", CashierPageView.class);

        appContext.getPages().put("cashier", pageEntry);
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
