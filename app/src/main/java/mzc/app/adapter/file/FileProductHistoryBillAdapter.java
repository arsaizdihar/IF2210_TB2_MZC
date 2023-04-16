package mzc.app.adapter.file;

import lombok.NonNull;
import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.adapter.base.IProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FileProductHistoryBillAdapter<T extends IFileDataLoader<ProductHistoryBill>> extends FileModelAdapter<ProductHistoryBill, T> implements IProductHistoryBillAdapter {
    private final @NonNull IProductHistoryAdapter productAdapter;

    public FileProductHistoryBillAdapter(@NonNull IProductHistoryAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    @Override
    protected Class<ProductHistoryBill> getType() {
        return ProductHistoryBill.class;
    }

    public List<ProductHistoryBill> getByBillId(long billId) {
        List<ProductHistoryBill> result = getData().values().stream().filter(p -> p.getBillId() == billId).collect(Collectors.toList());
        result.forEach(p -> p.setProduct(productAdapter.getById(p.getId())));
        return result;
    }
}
