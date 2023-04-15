package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.model.Bill;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {
    @Override
    public Bill getById(long id) {
        return super.getById(id);
    }

    @Override
    public void persist(@NonNull Bill member) {

    }

    @Override
    public @NonNull Set<Bill> getByMemberId(@NonNull Long memberId) {
        return getData().values().stream().filter((v) -> Objects.equals(v.getMemberId(), memberId)).collect(Collectors.toSet());
    }

    @Override
    protected @NonNull Class<Bill> getType() {
        return Bill.class;
    }
}
