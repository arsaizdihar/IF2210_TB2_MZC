package mzc.app.adapter.obj;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.adapter.file.FileProductBillAdapter;
import mzc.app.model.Bill;
import mzc.app.model.FixedBill;

class BillAdapter extends FileBillAdapter<OBJLoader<Bill>> {
    private static final OBJLoader<Bill> loader = new OBJLoader<>();

    @Getter
    private final @NonNull ProductBillAdapter productBillAdapter;

    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NonNull OBJLoader<Bill> getLoader() {
        return loader;
    }
}
