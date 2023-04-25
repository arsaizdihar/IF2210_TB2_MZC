package mzc.app.setting;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.AdapterType;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Setting implements Serializable {
    public Setting(@NonNull AdapterType storageMethod, @NonNull String JSONPath, @NonNull String XMLPath,
                   @NonNull String OBJPath, @NonNull String sqlOrmDatabaseUrl, @NonNull String sqlRawDatabaseUrl,
                   @NonNull List<String> activePlugins) {
        this.storageMethod = storageMethod;
        this.JSONPath = JSONPath;
        this.XMLPath = XMLPath;
        this.OBJPath = OBJPath;
        this.sqlOrmDatabaseUrl = sqlOrmDatabaseUrl;
        this.sqlRawDatabaseUrl = sqlRawDatabaseUrl;
        this.activePlugins = activePlugins;
    }

    @Getter @NonNull
    AdapterType storageMethod;

    @Getter @NonNull
    String JSONPath;

    @Getter @NonNull
    String XMLPath;

    @Getter @NonNull
    String OBJPath;

    @Getter @NonNull
    String sqlOrmDatabaseUrl;

    @Getter @NonNull
    String sqlRawDatabaseUrl;

    @Getter @NonNull
    List<String> activePlugins;

    public void save() {
        String filename = getSettingPath();

        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(this);

            out.close();
            file.close();
        } catch (IOException ignored) {
            System.out.println("Failed to save configuration file");
        }
    }

    private static String getSettingPath() {
        return Paths.get("./setting.app").toAbsolutePath().toString();
    }

    public static @NonNull Setting load() {
        String filename = getSettingPath();

        Setting result;

        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            result = (Setting) in.readObject();

            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException ignored) {
            result = new Setting(
                    AdapterType.OBJ,
                    AdapterConfig.getBaseDataPath(),
                    AdapterConfig.getBaseDataPath(),
                    AdapterConfig.getBaseDataPath(),
                    "jdbc:mysql://root:root@localhost:3306/mzc",
                    "jdbc:mysql://root:root@localhost:3306/mzcraw",
                    new ArrayList<>()
            );
        }

        return result;
    }
}
