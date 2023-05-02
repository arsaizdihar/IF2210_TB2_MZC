package mzc.plugin_system1.adapter.file;

import mzc.app.adapter.file.FileModelAdapter;
import mzc.app.adapter.file.IFileDataLoader;
import mzc.plugin_system1.adapter.base.ICurrencyAdapter;
import mzc.plugin_system1.model.Currency;
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
