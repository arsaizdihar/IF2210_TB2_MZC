package mzc.plugin_charge.adapter.file;

import mzc.app.adapter.xml.XMLLoader;
import org.jetbrains.annotations.NotNull;

public class XMLChargeAdapter extends FileChargeAdapter {
    private static final @NotNull XMLLoader loader = new XMLLoader();

    public XMLChargeAdapter() {
        super(loader);
    }
}
