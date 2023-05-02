package mzc.plugin_system1;

import lombok.Getter;
import mzc.app.bootstrap.App;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system1.model.Currency;
import org.hibernate.Session;

public class CurrencyPlugin extends Plugin {
    @Getter
    protected static AppSetting setting;

    @Getter
    protected static Session session;

    public CurrencyPlugin() {
        super("Multicurrency");
    }

    @Override
    public void setup(App appContext) {
        setting = appContext.getAppSetting();

        appContext.getHibernateConfiguration().addClass(Currency.class);
    }

    @Override
    public void postSetup(Session hibernateSession) {
        session = hibernateSession;
    }
}
