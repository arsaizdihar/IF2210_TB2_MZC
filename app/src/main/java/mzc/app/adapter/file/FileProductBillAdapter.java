package mzc.app.adapter.file;

import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.base.IProductBillAdapter;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileProductBillAdapter extends FileModelAdapter<ProductBill> implements IProductBillAdapter {
    private final @NotNull IProductAdapter productAdapter;

    public FileProductBillAdapter(@NotNull IFileDataLoader loader, @NotNull IProductAdapter productAdapter) {
        super(loader);
        this.productAdapter = productAdapter;
    }

    @Override
    protected @NotNull Class<ProductBill> getType() {
        return ProductBill.class;
    }

    public Set<ProductBill> getByBillId(long billId) {
        Set<ProductBill> result = getClones(getData().values().stream().filter(p -> p.getBillId() == billId));
        result.forEach(p -> p.setProduct(productAdapter.getById(p.getProductId())));
        return result;
    }
}
