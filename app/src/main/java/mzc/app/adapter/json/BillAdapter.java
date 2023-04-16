package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Product;

class BillAdapter extends FileBillAdapter<JSONLoader<Bill>> {
    private static final @NonNull JSONLoader<Bill> loader = new JSONLoader<>();

    @Getter
    private final @NonNull ProductBillAdapter productBillAdapter;
    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull JSONLoader<Bill> getLoader() {
        return loader;
    }
}
