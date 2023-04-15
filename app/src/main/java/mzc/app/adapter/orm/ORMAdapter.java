package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.base.IMemberAdapter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ORMAdapter implements IMainAdapter {

    private final MemberAdapter memberAdapter;
    private final Session session;

    public ORMAdapter() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        memberAdapter = new MemberAdapter(session);
    }

    @NonNull
    @Override
    public IMemberAdapter getMember() {
        return memberAdapter;
    }

    @Override
    public void close() {
        session.close();
    }
}
