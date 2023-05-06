package mzc.app.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.model.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProductAdapter extends ModelAdapter<Product> implements IProductAdapter {
    public ProductAdapter(@NotNull HikariDataSource ds) {
        super(ds);
    }

    @Override
    protected Class<Product> getType() {
        return Product.class;
    }

    @Override
    public @NotNull Set<String> getCategories() {
        try (var con = ds.getConnection(); var stmt = con.prepareStatement("SELECT DISTINCT category FROM product"); var rs = stmt.executeQuery()) {
            Set<String> categories = new LinkedHashSet<>();
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
            return categories;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
