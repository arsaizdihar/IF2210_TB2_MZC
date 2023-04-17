package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.adapter.base.IProductHistoryBillAdapter;
import mzc.app.model.ProductHistoryBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FileProductHistoryBillAdapter<T extends IFileDataLoader<ProductHistoryBill>> extends FileModelAdapter<ProductHistoryBill, T> implements IProductHistoryBillAdapter {
    private final @NotNull IProductHistoryAdapter productAdapter;

    public FileProductHistoryBillAdapter(@NotNull IProductHistoryAdapter productAdapter) {
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