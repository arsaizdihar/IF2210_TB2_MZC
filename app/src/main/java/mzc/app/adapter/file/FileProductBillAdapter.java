package mzc.app.adapter.file;

import lombok.NonNull;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.base.IProductBillAdapter;
import mzc.app.model.Product;
import mzc.app.model.ProductBill;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FileProductBillAdapter <T extends IFileDataLoader<ProductBill>> extends FileModelAdapter<ProductBill, T> implements IProductBillAdapter {
    private final @NonNull IProductAdapter productAdapter;

    public FileProductBillAdapter(@NonNull IProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    @Override
    protected @NonNull Class<ProductBill> getType() {
        return ProductBill.class;
    }

    public List<ProductBill> getByBillId(long billId) {
        List<ProductBill> result = getData().values().stream().filter(p -> p.getBillId() == billId).collect(Collectors.toList());
        result.forEach(p -> p.setProduct(productAdapter.getById(p.getId())));
        return result;
    }

}
