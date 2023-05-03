package mzc.plugin_currency.adapter.file;

import mzc.app.adapter.file.FileModelAdapter;
import mzc.app.adapter.file.IFileDataLoader;
import mzc.plugin_currency.adapter.base.ICurrencyAdapter;
import mzc.plugin_currency.model.Currency;
import org.jetbrains.annotations.NotNull;

public class FileCurrencyAdapter extends FileModelAdapter<Currency> implements ICurrencyAdapter {
    protected FileCurrencyAdapter(@NotNull IFileDataLoader loader) {
        super(loader);
    }

    @Override
    protected @NotNull Class<Currency> getType() {
        return Currency.class;
    }
}
