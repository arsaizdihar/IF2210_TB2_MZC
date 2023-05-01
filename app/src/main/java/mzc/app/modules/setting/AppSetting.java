package mzc.app.modules.setting;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.AdapterType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;

public class AppSetting extends BaseSetting {
    public AppSetting(@NonNull AdapterType storageMethod, @NonNull String JSONPath, @NonNull String XMLPath,
                      @NonNull String OBJPath, @NonNull String sqlOrmDatabaseUrl, @NonNull String sqlRawDatabaseUrl
    ) {
        super(getDefaultPath());
        this.storageMethod = storageMethod;
        this.JSONPath = JSONPath;
        this.XMLPath = XMLPath;
        this.OBJPath = OBJPath;
        this.sqlOrmDatabaseUrl = sqlOrmDatabaseUrl;
        this.sqlRawDatabaseUrl = sqlRawDatabaseUrl;
    }

    @Getter
    @NonNull
    AdapterType storageMethod;

    @Getter
    @NonNull
    String JSONPath;

    @Getter
    @NonNull
    String XMLPath;

    @Getter
    @NonNull
    String OBJPath;

    @Getter
    @NonNull
    String sqlOrmDatabaseUrl;

    @Getter
    @NonNull
    String sqlRawDatabaseUrl;


    private static String getDefaultPath() {
        return Paths.get("./setting.app").toAbsolutePath().toString();
    }

    public static AppSetting load() {
        AppSetting result;

        try {
            FileInputStream file = new FileInputStream(getDefaultPath());
            ObjectInputStream in = new ObjectInputStream(file);

            result = (AppSetting) in.readObject();

            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException ignored) {
            result = new AppSetting(
                    AdapterType.OBJ,
                    AdapterConfig.getBaseDataPath(),
                    AdapterConfig.getBaseDataPath(),
                    AdapterConfig.getBaseDataPath(),
                    "jdbc:mysql://root:root@localhost:3306/mzc",
                    "jdbc:mysql://root:root@localhost:3306/mzcraw"
            );
        }

        return result;
    }
}
