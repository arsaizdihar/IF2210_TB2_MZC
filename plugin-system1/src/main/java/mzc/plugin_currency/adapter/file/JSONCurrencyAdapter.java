package mzc.plugin_currency.adapter.file;

import mzc.app.adapter.json.JSONLoader;
import org.jetbrains.annotations.NotNull;

public class JSONCurrencyAdapter extends FileCurrencyAdapter {
    private static final @NotNull JSONLoader loader = new JSONLoader();

    public JSONCurrencyAdapter() {
        super(loader);
    }
}
