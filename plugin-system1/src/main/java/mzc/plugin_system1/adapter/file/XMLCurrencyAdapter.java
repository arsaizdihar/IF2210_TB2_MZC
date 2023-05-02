package mzc.plugin_system1.adapter.file;

import mzc.app.adapter.xml.XMLLoader;
import org.jetbrains.annotations.NotNull;

public class XMLCurrencyAdapter extends FileCurrencyAdapter {
    private static final @NotNull XMLLoader loader = new XMLLoader();

    public XMLCurrencyAdapter() {
        super(loader);
    }
}
