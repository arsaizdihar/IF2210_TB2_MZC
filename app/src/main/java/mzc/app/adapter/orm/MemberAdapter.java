package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.IMemberAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Member;
import org.hibernate.Session;

import java.util.Set;

class MemberAdapter extends ModelAdapter<Member> implements IMemberAdapter {
    public MemberAdapter(Session session) {
        super(session);
    }

    @Override
    public Member getById(long id) {
        return super.getById(id);
    }

    @NonNull
    @Override
    protected Class<Member> getType() {
        return Member.class;
    }

    @Override
    public Set<Bill> getBills(@NonNull Member member) {
        return member.getBills();
    }
}
