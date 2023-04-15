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
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        member = new MemberAdapter(session);
        bill = new BillAdapter(session);
    }

    @Override
    public void close() {
        session.close();
    }
}
