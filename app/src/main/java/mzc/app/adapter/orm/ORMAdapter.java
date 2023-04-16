package mzc.app.adapter.orm;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IMainAdapter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ORMAdapter implements IMainAdapter {

    @Getter
    private final @NonNull CustomerAdapter customer;
    @Getter
    private final @NonNull BillAdapter bill;
    @Getter
    private final @NonNull ProductAdapter product;
    @Getter
    private final @NonNull ProductHistoryAdapter productHistory;
    @Getter
    private final @NonNull FixedBillAdapter fixedBill;
    private final @NonNull Session session;

    public ORMAdapter() {
        Configuration cfg = new Configuration();
        cfg.configure();

        if (cfg.getProperty("hibernate.connection.url") == null) {
            cfg.setProperty("hibernate.connection.url",
                    System.getenv().getOrDefault("DATABASE_URL", "jdbc:mysql://root:root@localhost:3306/mzc"));
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
