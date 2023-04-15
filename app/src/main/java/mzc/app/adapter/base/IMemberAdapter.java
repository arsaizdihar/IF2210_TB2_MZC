package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.Member;

public interface IMemberAdapter {

    public Member getById(long id);

    public void persist(@NonNull Member member);
}
