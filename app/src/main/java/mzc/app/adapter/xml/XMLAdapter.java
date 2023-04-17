package mzc.app.adapter.xml;

import lombok.Getter;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.file.FileAdapter;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class XMLAdapter extends FileAdapter {
    private static final @NotNull XMLLoader loader = new XMLLoader();

    public XMLAdapter() {
        super(loader);
    }
}