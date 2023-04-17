package mzc.app.adapter.json;

import lombok.Getter;
import mzc.app.adapter.file.FileFixedBillAdapter;
import mzc.app.model.FixedBill;
import org.jetbrains.annotations.NotNull;

class FixedBillAdapter extends FileFixedBillAdapter<JSONLoader<FixedBill>> {
    private static final @NotNull JSONLoader<FixedBill> loader = new JSONLoader<>();

    @Getter
    private final @NotNull ProductHistoryBillAdapter productBillAdapter;

    FixedBillAdapter(@NotNull ProductHistoryAdapter productAdapter) {
        this.productBillAdapter = new ProductHistoryBillAdapter(productAdapter);
    }

    @Override
    protected @NotNull JSONLoader<FixedBill> getLoader() {
        return loader;
    }
}
