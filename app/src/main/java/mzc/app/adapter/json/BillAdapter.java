package mzc.app.adapter.json;

import lombok.Getter;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.model.Bill;
import org.jetbrains.annotations.NotNull;

class BillAdapter extends FileBillAdapter<JSONLoader<Bill>> {
    private static final @NotNull JSONLoader<Bill> loader = new JSONLoader<>();

    @Getter
    private final @NotNull ProductBillAdapter productBillAdapter;
    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NotNull JSONLoader<Bill> getLoader() {
        return loader;
    }
}
