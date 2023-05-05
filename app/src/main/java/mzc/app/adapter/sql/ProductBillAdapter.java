package mzc.app.adapter.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.base.IProductAdapter;
import mzc.app.adapter.base.IProductBillAdapter;
import mzc.app.model.BaseModel;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductBillAdapter extends ModelAdapter<ProductBill> implements IProductBillAdapter {
    private final @NotNull IProductAdapter productAdapter;
    public ProductBillAdapter(@NotNull HikariDataSource ds, @NotNull IProductAdapter productAdapter) {
        super(ds);
        this.productAdapter = productAdapter;
    }

    @Override
    protected Class<ProductBill> getType() {
        return ProductBill.class;
    }

    public Set<ProductBill> getByBillId(long billId) {
        var query = SqlFormatter.format("SELECT * FROM productbill WHERE billId = ?",
                Collections.singletonList(billId));
        try (var con = this.ds.getConnection(); var stmt = con.prepareStatement(query); var rs = stmt.executeQuery()) {
            var models = deserializeResults(rs);
            var ids = models.stream().map(ProductBill::getProductId).collect(Collectors.toSet());
            var products = productAdapter.getInIds(ids);
            var map = products.stream().collect(Collectors.toMap(BaseModel::getId, p -> p));
            models.forEach(p -> p.setProduct(map.get(p.getProductId())));
            return models;
        } catch (SQLException e) {
            return new LinkedHashSet<>();
        }
    }
}
