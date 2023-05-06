package mzc.app.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.modules.setting.AppSettingManager;

public class ConnectionManager {
    private static HikariDataSource datastore = null;

    public static HikariDataSource getDatastore() {
        if (datastore == null) {
            throw new RuntimeException("Datastore was not set");
        }

        return datastore;
    }

    public static void setupConnection() {
        datastore = new HikariDataSource();
        datastore.setJdbcUrl(AppSettingManager.get().getSqlRawDatabaseUrl());
    }
}
