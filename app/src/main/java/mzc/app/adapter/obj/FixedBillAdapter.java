package mzc.app.adapter.obj;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileFixedBillAdapter;
import mzc.app.model.FixedBill;

class FixedBillAdapter extends FileFixedBillAdapter<OBJLoader<FixedBill>> {
    private static final OBJLoader<FixedBill> loader = new OBJLoader<>();

    @Getter
    private final @NonNull ProductHistoryBillAdapter productBillAdapter;

    FixedBillAdapter(@NonNull ProductHistoryAdapter productAdapter) {
        this.productBillAdapter = new ProductHistoryBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull OBJLoader<FixedBill> getLoader() {
        return loader;
    }
}
