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

    @Override
    public void persist(@NotNull ProductBill item) {
        if (item.getId() == 0) {
            item.getProduct().getBills().add(item);
            item.getBill().getProducts().add(item);
        }
        super.persist(item);
    }

    @Override
    public void delete(@NotNull ProductBill model) {
        model.getProduct().getBills().remove(model);
        super.delete(model);
    }
}
