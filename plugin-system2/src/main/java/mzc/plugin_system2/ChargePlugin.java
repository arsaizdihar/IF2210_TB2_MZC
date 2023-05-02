package mzc.plugin_system2;

import lombok.Getter;
import mzc.app.bootstrap.App;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system2.adapter.ChargeManager;
import mzc.plugin_system2.models.Charge;
import org.hibernate.Session;

public class ChargePlugin extends Plugin {
    @Getter
    protected static AppSetting appSetting;

    @Getter
    protected static Session session;

    public ChargePlugin() {
        super("Charge Plugin");
    }

    @Override
    public void setup(App appContext) {
        appSetting = appContext.getAppSetting();

        appContext.getHibernateConfiguration().addClass(Charge.class);
    }

    @Override
    public void postSetup(Session hibernateSession) {
        session = hibernateSession;

        var adapter = ChargeManager.getAdapter();

        if (adapter.getAll().size() == 0) {
            for (var charge : ChargeManager.getChargeSeed()) {
                adapter.persist(charge);
            }
        }

        ChargeManager.loadCharges();
    }
}
