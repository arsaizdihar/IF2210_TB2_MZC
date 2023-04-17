package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.base.IProductBillAdapter;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FileProductBillAdapter <T extends IFileDataLoader<ProductBill>> extends FileModelAdapter<ProductBill, T> implements IProductBillAdapter {
    private final @NotNull IProductAdapter productAdapter;

    public FileProductBillAdapter(@NotNull IProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    @Override
    protected @NotNull Class<ProductBill> getType() {
        return ProductBill.class;
    }

    public List<ProductBill> getByBillId(long billId) {
        List<ProductBill> result = getData().values().stream().filter(p -> p.getBillId() == billId).collect(Collectors.toList());
        result.forEach(p -> p.setProduct(productAdapter.getById(p.getId())));
        return result;
    }

}
