package mzc.plugin_charge.adapter.file;

import lombok.NonNull;
import mzc.app.adapter.file.FileModelAdapter;
import mzc.app.adapter.file.IFileDataLoader;
import mzc.plugin_charge.adapter.base.IChargeAdapter;
import mzc.plugin_charge.models.Charge;
import org.jetbrains.annotations.NotNull;

public class FileChargeAdapter extends FileModelAdapter<Charge> implements IChargeAdapter {
    protected FileChargeAdapter(@NonNull IFileDataLoader loader) {
        super(loader);
    }

    @Override
    protected @NotNull Class<Charge> getType() {
        return Charge.class;
    }
}
