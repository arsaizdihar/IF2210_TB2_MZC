package mzc.plugin_currency.adapter.file;

import mzc.app.adapter.obj.OBJLoader;
import org.jetbrains.annotations.NotNull;

public class OBJCurrencyAdapter extends FileCurrencyAdapter {
    private static final @NotNull OBJLoader loader = new OBJLoader();

    public OBJCurrencyAdapter() {
        super(loader);
    }
}
