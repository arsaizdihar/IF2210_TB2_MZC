package mzc.plugin_system2;

import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
import mzc.app.adapter.sql.Schema;
import mzc.app.bootstrap.App;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system2.adapter.ChargeManager;
import mzc.plugin_system2.models.Charge;

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
            appContext.getHibernateConfiguration().addClass(Charge.class);
        } else if (appSetting.getStorageMethod() == AdapterType.SQLRaw) {
            Schema.setValue(Schema.getValue() + " " + mzc.plugin_system2.adapter.sql.Schema.getValue());
        }
    }

    @Override
    public void postSetup() {
        var adapter = ChargeManager.getAdapter();

        if (adapter.getAll().size() == 0) {
            for (var charge : ChargeManager.getChargeSeed()) {
                adapter.persist(charge);
            }
        }

        ChargeManager.loadCharges();
    }
}
