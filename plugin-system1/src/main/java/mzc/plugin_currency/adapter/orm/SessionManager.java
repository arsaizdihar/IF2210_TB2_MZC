package mzc.plugin_currency.adapter.orm;

import lombok.Getter;
import lombok.Setter;
import mzc.plugin_currency.CurrencyPlugin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionManager {
    private static Configuration configuration;
    private static Session session;

    @Getter
    @Setter
    private static boolean updateUrl = true;

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
            configuration.configure();

            if (configuration.getProperty("hibernate.connection.url") == null && updateUrl) {
                configuration.setProperty("hibernate.connection.url", CurrencyPlugin.getAppSetting().getSqlOrmDatabaseUrl());
            }
        }

        return configuration;
    }

    public static Session getSession() {
        if (session == null) {
            if (configuration == null) {
                throw new RuntimeException("Configuration not set");
            }

            SessionFactory sessionFactory = configuration.buildSessionFactory();
            session = sessionFactory.openSession();
        }

        return session;
    }

    public static void closeSession() {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception ignored) {
        }

        session = null;
    }
}
