package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileFixedBillAdapter;
import mzc.app.model.FixedBill;

class FixedBillAdapter extends FileFixedBillAdapter<JSONLoader<FixedBill>> {
    private static final @NonNull JSONLoader<FixedBill> loader = new JSONLoader<>();

    @Getter
    private final @NonNull ProductHistoryBillAdapter productBillAdapter;

    FixedBillAdapter(@NonNull ProductHistoryAdapter productAdapter) {
        this.productBillAdapter = new ProductHistoryBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull JSONLoader<FixedBill> getLoader() {
        return loader;
    }
}
