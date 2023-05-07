package mzc.plugin_currency;

import lombok.Getter;
import mzc.app.adapter.base.AdapterType;
import mzc.app.adapter.sql.Schema;
import mzc.app.bootstrap.App;
import mzc.app.modules.plugins.Plugin;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_currency.adapter.CurrencyManager;
import mzc.plugin_currency.model.CurrencyPrice;
import mzc.plugin_currency.view.CurrencySettingView;

public class CurrencyPlugin extends Plugin {

    @Getter
    private static AppSetting appSetting;

    public CurrencyPlugin() {
        super("Multicurrency");
    }

    @Override
    public void setup(App appContext) {
        appSetting = appContext.getAppSetting();

        if (appSetting.getStorageMethod() == AdapterType.SQLRaw) {
            Schema.setValue(Schema.getValue() + " " + mzc.plugin_currency.adapter.sql.Schema.getValue());
        }

        appContext.getSettingTabs().put("Currency", CurrencySettingView.class);
    }

    @Override
    public void postSetup() {
        var adapter = CurrencyManager.getAdapter();

        if (adapter.getAll().size() == 0) {
            for (var currency : CurrencyManager.getCurrencySeed()) {
                adapter.persist(currency);
            }
        }

        CurrencyManager.loadDefault();

        try {
            PriceFactory.setPriceView(CurrencyPrice.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize currency price view");
        }
    }
}
