package mzc.app.adapter.obj;

import lombok.Getter;
import mzc.app.adapter.file.FileFixedBillAdapter;
import mzc.app.model.FixedBill;
import org.jetbrains.annotations.NotNull;

class FixedBillAdapter extends FileFixedBillAdapter<OBJLoader<FixedBill>> {
    private static final OBJLoader<FixedBill> loader = new OBJLoader<>();

    @Getter
    private final @NotNull ProductHistoryBillAdapter productBillAdapter;

    FixedBillAdapter(@NotNull ProductHistoryAdapter productAdapter) {
        this.productBillAdapter = new ProductHistoryBillAdapter(productAdapter);
    }

    @Override
    protected @NotNull OBJLoader<FixedBill> getLoader() {
        return loader;
    }
}
