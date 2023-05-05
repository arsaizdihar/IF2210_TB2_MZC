package mzc.app.adapter.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedBillAdapter extends ModelAdapter<FixedBill> implements IFixedBillAdapter {
    private final @NotNull ProductHistoryAdapter productHistoryAdapter;
    public FixedBillAdapter(@NotNull HikariDataSource ds, @NotNull ProductHistoryAdapter productHistoryAdapter) {
        super(ds);
        this.productHistoryAdapter = productHistoryAdapter;
    }

    @Override
    public @NotNull List<FixedBill> getByCustomerId(long customerId) {
        var query = SqlFormatter.format("SELECT * FROM fixedbill WHERE customerId = ?",
                Collections.singletonList(customerId));
        try (var con = this.ds.getConnection(); var stmt = con.prepareStatement(query); var rs = stmt.executeQuery()) {
            return deserializeResults(rs);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public @NotNull List<ProductHistory> getProducts(FixedBill bill) {
        if (bill.isProductsLoaded()) {
            return bill.getProducts();
        }
        var products = productHistoryAdapter.getByBillId(bill.getId());
        bill.setProducts(products);
        bill.setProductsLoaded(true);
        return products;
    }

    @Override
    protected Class<FixedBill> getType() {
        return FixedBill.class;
    }
}
