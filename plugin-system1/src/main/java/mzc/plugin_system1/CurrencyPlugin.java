package mzc.plugin_system1;

import lombok.Getter;
import mzc.app.bootstrap.App;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system1.adapter.CurrencyManager;
import mzc.plugin_system1.model.Currency;
import mzc.plugin_system1.setting.CurrencySetting;
import org.hibernate.Session;

public class CurrencyPlugin extends Plugin {
    @Getter
    protected static AppSetting appSetting;

    @Getter
    protected static CurrencySetting setting;

    @Getter
    protected static Session session;

    public CurrencyPlugin() {
        super("Multicurrency");
    }

    @Override
    public void setup(App appContext) {
        appSetting = appContext.getAppSetting();
        setting = CurrencySetting.load();

        appContext.getHibernateConfiguration().addClass(Currency.class);
    }

    @Override
    public void postSetup(Session hibernateSession) {
        session = hibernateSession;

        var adapter = CurrencyManager.getAdapter();

        if (adapter.getAll().size() == 0) {
            for (var currency : CurrencySetting.getCurrencySeed()) {
                adapter.persist(currency);
            }
        }
    }
}
