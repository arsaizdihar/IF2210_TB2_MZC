package mzc.plugin_system2.adapter.file;

import mzc.app.adapter.json.JSONLoader;
import org.jetbrains.annotations.NotNull;

public class JSONChargeAdapter extends FileChargeAdapter {
    private static final @NotNull JSONLoader loader = new JSONLoader();

    public JSONChargeAdapter() {
        super(loader);
    }
}
