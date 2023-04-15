package mzc.app.adapter.base;

import lombok.NonNull;
import mzc.app.model.Bill;

import java.util.Set;

public interface IBillAdapter extends IBasicAdapter<Bill> {

    @NonNull Set<Bill> getByMemberId(@NonNull Long memberId);
}
