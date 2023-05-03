package mzc.app.adapter.orm;

import mzc.app.adapter.base.IProductBillAdapter;
import mzc.app.model.ProductBill;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

public class ProductBillAdapter extends ModelAdapter<ProductBill> implements IProductBillAdapter {
    public ProductBillAdapter(Session session) {
        super(session);
    }

    @Override
    protected @NotNull Class<ProductBill> getType() {
        return ProductBill.class;
    }
}
