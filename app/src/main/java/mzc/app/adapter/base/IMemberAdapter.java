package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.Bill;
import mzc.app.model.Member;

import java.util.Set;

public interface IMemberAdapter extends IBasicAdapter<Member> {
    Set<Bill> getBills(@NonNull Member member);
}
