package mzc.plugin_system1.adapter;

import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system1.CurrencyPlugin;
import mzc.plugin_system1.adapter.base.ICurrencyAdapter;
import mzc.plugin_system1.adapter.file.JSONCurrencyAdapter;
import mzc.plugin_system1.adapter.file.OBJCurrencyAdapter;
import mzc.plugin_system1.adapter.file.XMLCurrencyAdapter;
import mzc.plugin_system1.adapter.orm.CurrencyAdapter;
import org.jetbrains.annotations.NotNull;

public class CurrencyManager {
    protected static ICurrencyAdapter adapter;

    public static @NotNull ICurrencyAdapter getAdapter() {
        if (adapter == null) {
            AppSetting setting = CurrencyPlugin.getSetting();

            switch (setting.getStorageMethod()) {
                case OBJ -> adapter = new OBJCurrencyAdapter();
                case XML -> adapter = new XMLCurrencyAdapter();
                case JSON -> adapter = new JSONCurrencyAdapter();
                case SQLORM -> adapter = new CurrencyAdapter(CurrencyPlugin.getSession());
                case SQLRaw -> adapter = new CurrencyAdapter(CurrencyPlugin.getSession()); // temporary
                default -> adapter = new JSONCurrencyAdapter();
            }
        }

        return adapter;
    }
}
