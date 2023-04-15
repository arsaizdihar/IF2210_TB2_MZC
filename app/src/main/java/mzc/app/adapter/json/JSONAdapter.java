package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.adapter.base.IMemberAdapter;

public class JSONAdapter implements IMainAdapter {
    private final MemberAdapter memberAdapter;
    private final BillAdapter billAdapter;

    public JSONAdapter() {
        this.billAdapter = new BillAdapter();
        this.memberAdapter = new MemberAdapter(billAdapter);
    }

    @Override
    public @NonNull IMemberAdapter getMember() {
        return this.memberAdapter;
    }

    @Override
    public @NonNull IBillAdapter getBill() {
        return billAdapter;
    }

    @Override
    public void close() {

    }
}
