package mzc.app.adapter.obj;

import lombok.Getter;
import mzc.app.adapter.file.FileBillAdapter;
import mzc.app.model.Bill;
import org.jetbrains.annotations.NotNull;

class BillAdapter extends FileBillAdapter<OBJLoader<Bill>> {
    private static final OBJLoader<Bill> loader = new OBJLoader<>();

    @Getter
    private final @NotNull ProductBillAdapter productBillAdapter;

    public BillAdapter(ProductAdapter productAdapter) {
        this.productBillAdapter = new ProductBillAdapter(productAdapter);
    }

    @Override
    protected @NotNull OBJLoader<Bill> getLoader() {
        return loader;
    }
}
