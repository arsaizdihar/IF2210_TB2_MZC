package mzc.app.adapter.orm;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IMainAdapter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ORMAdapter implements IMainAdapter {

    @Getter private final @NonNull MemberAdapter member;
    @Getter private final @NonNull BillAdapter bill;
    private final Session session;

    public ORMAdapter() {
        Configuration cfg = new Configuration();
        cfg.configure();

        System.out.println(cfg.getProperty("hibernate.connection.url"));

        if (cfg.getProperty("hibernate.connection.url") == null) {
            cfg.setProperty("hibernate.connection.url",
                    System.getenv().getOrDefault("DATABASE_URL", "jdbc:mysql://root:root@localhost:3306/mzc"));
        }

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        member = new MemberAdapter(session);
        bill = new BillAdapter(session);
    }

    @Override
    public void close() {
        session.close();
    }
}
