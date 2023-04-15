package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.IMemberAdapter;
import mzc.app.model.Member;
import org.hibernate.Session;

class MemberAdapter implements IMemberAdapter {
    private final Session session;

    public MemberAdapter(Session session) {
        this.session = session;
    }

    @Override
    public Member getById(long id) {
        return session.get(Member.class, id);
    }

    @Override
    public void persist(@NonNull Member member) {
        session.persist(member);
    }

}
