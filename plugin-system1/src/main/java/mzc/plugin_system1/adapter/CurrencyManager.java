package mzc.plugin_system1.adapter;

import lombok.Getter;
import lombok.Setter;
import mzc.app.modules.setting.AppSetting;
import mzc.plugin_system1.CurrencyPlugin;
import mzc.plugin_system1.adapter.base.ICurrencyAdapter;
import mzc.plugin_system1.adapter.file.JSONCurrencyAdapter;
import mzc.plugin_system1.adapter.file.OBJCurrencyAdapter;
import mzc.plugin_system1.adapter.file.XMLCurrencyAdapter;
import mzc.plugin_system1.adapter.orm.CurrencyAdapter;
import mzc.plugin_system1.model.Currency;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CurrencyManager {
    protected static ICurrencyAdapter adapter;

    @Getter
    @Setter
    protected static Currency defaultCurrency;

    public static @NotNull ICurrencyAdapter getAdapter() {
        if (adapter == null) {
            AppSetting setting = CurrencyPlugin.getAppSetting();

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

    public static List<Currency> getCurrencySeed() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("Rp", "Rupiah", new BigDecimal(1), true));
        currencies.add(new Currency("$", "Dollar", new BigDecimal("14740.15")));
        currencies.add(new Currency("㍐", "Yuan", new BigDecimal("2132.51")));
        return currencies;
    }

    public static Currency loadDefault() {
        var currency = adapter.getAll().stream().filter(Currency::isDefault).findFirst();

        if (currency.isEmpty()) {
            var alternative = new Currency("Rp", "Rupiah", new BigDecimal(1), true);
            adapter.persist(alternative);

            defaultCurrency = alternative;
        } else {
            defaultCurrency = currency.get();
        }

        return defaultCurrency;
    }
}
