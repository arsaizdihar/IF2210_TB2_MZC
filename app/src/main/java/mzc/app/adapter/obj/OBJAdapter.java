package mzc.app.adapter.obj;

import mzc.app.adapter.file.FileAdapter;
import org.jetbrains.annotations.NotNull;

public class OBJAdapter extends FileAdapter {
    private static final @NotNull OBJLoader loader = new OBJLoader();

    public OBJAdapter() {
        super(loader);
    }
}
