package mzc.app.modules.setting;

import lombok.NonNull;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseSetting implements Serializable {
    protected final @NonNull String settingPath;

    public BaseSetting(@NonNull String settingPath) {
        this.settingPath = settingPath;
    }

    public void save() {
        try {
            Files.createDirectories(Paths.get(settingPath).getParent().toAbsolutePath());
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
