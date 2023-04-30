package mzc.app.adapter.orm;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.modules.setting.AppSetting;
import mzc.app.modules.setting.AppSettingManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jetbrains.annotations.NotNull;

public class ORMAdapter implements IMainAdapter {

    @Getter
    private final @NotNull CustomerAdapter customer;
    @Getter
    private final @NotNull BillAdapter bill;
    @Getter
    private final @NotNull ProductAdapter product;
    @Getter
    private final @NotNull ProductHistoryAdapter productHistory;
    @Getter
    private final @NotNull FixedBillAdapter fixedBill;
    private final @NotNull Session session;

    public ORMAdapter() {
        Configuration cfg = new Configuration();
        cfg.configure();
        AppSetting setting = AppSettingManager.get();

        if (cfg.getProperty("hibernate.connection.url") == null) {
            cfg.setProperty("hibernate.connection.url", setting.getSqlOrmDatabaseUrl());
        }

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        customer = new CustomerAdapter(session);
        bill = new BillAdapter(session);
        product = new ProductAdapter(session);
        productHistory = new ProductHistoryAdapter(session);
        fixedBill = new FixedBillAdapter(session);
    }

    @Override
    public void close() {
        session.close();
    }

    @Override
    public void clearData() {
    }
}
