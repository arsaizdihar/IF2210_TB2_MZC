package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IMemberAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Member;

import java.util.Set;

class MemberAdapter extends ModelAdapter<Member> implements IMemberAdapter {
    private final @NonNull BillAdapter billAdapter;

    MemberAdapter(@NonNull BillAdapter billAdapter) {
        this.billAdapter = billAdapter;
    }

    @Override
    public Member getById(long id) {
        return super.getById(id);
    }

    @Override
    public void persist(@NonNull Member member) {

    }

    @Override
    protected @NonNull Class<Member> getType() {
        return Member.class;
    }

    @Override
    public Set<Bill> getBills(@NonNull Member member) {
        if (member.isBillsLoaded()) return member.getBills();
        member.setBillsLoaded(true);
        Set<Bill> result = billAdapter.getByMemberId(member.getId());
        member.setBills(result);
        return result;
    }
}
