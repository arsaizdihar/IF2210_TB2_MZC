package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.model.ProductBill;

import java.util.List;

class ProductBillAdapter extends ModelAdapter<ProductBill> {
    private final @NonNull ProductAdapter productAdapter;

    ProductBillAdapter(@NonNull ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    @Override
    protected @NonNull Class<ProductBill> getType() {
        return ProductBill.class;
    }

    public List<ProductBill> getByBillId(long billId) {
        var result = getData().values().stream().filter(p -> p.getBillId() == billId).toList();
        result.forEach(p -> p.setProduct(productAdapter.getById(p.getId())));
        return result;
    }
}
