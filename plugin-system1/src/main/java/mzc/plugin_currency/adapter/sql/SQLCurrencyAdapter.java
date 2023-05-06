package mzc.plugin_currency.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.sql.ModelAdapter;
import mzc.plugin_currency.adapter.base.ICurrencyAdapter;
import mzc.plugin_currency.model.Currency;
import org.jetbrains.annotations.NotNull;

public class SQLCurrencyAdapter extends ModelAdapter<Currency> implements ICurrencyAdapter {
    public SQLCurrencyAdapter(@NotNull HikariDataSource ds) {
        super(ds);
    }

    @Override
    protected Class<Currency> getType() {
        return Currency.class;
    }
}
