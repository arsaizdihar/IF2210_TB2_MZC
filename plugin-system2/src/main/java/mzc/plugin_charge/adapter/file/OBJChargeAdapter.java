package mzc.plugin_charge.adapter.file;

import mzc.app.adapter.obj.OBJLoader;
import org.jetbrains.annotations.NotNull;

public class OBJChargeAdapter extends FileChargeAdapter {
    private static final @NotNull OBJLoader loader = new OBJLoader();

    public OBJChargeAdapter() {
        super(loader);
    }
}
