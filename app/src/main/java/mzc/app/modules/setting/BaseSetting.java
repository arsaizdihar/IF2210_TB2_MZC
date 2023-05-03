package mzc.app.modules.setting;

import lombok.NonNull;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class BaseSetting implements Serializable {
    protected final @NonNull String settingPath;

    public BaseSetting(@NonNull String settingPath) {
        this.settingPath = settingPath;
    }

    public void save() {
        try {
            FileOutputStream file = new FileOutputStream(this.settingPath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(this);

            out.close();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
