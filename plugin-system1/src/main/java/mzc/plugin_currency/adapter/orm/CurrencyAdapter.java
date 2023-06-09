package mzc.plugin_currency.adapter.orm;

import mzc.app.adapter.orm.ModelAdapter;
import mzc.plugin_currency.adapter.base.ICurrencyAdapter;
import mzc.plugin_currency.model.Currency;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

public class CurrencyAdapter extends ModelAdapter<Currency> implements ICurrencyAdapter {
    public CurrencyAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<Currency> getType() {
        return Currency.class;
    }
}
