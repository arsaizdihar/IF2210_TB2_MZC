package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.model.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {
    @Override
    public Bill getById(long id) {
        return super.getById(id);
    }

    @Override
    public @NonNull List<Bill> getByCustomerId(@NonNull Long customerId) {
        return getData().values().stream().filter((v) -> Objects.equals(v.getCustomerId(), customerId)).toList();
    }

    @Override
    public @NonNull List<Bill> getAll() {
        return new ArrayList<>(getData().values());
    }

    @Override
    protected @NonNull Class<Bill> getType() {
        return Bill.class;
    }
}
