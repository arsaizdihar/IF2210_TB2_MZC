package mzc.app.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public class ProductAdapter extends ModelAdapter<Product> implements IProductAdapter {
    public ProductAdapter(@NotNull HikariDataSource ds) {
        super(ds);
    }

    @Override
    protected Class<Product> getType() {
        return Product.class;
    }
}
