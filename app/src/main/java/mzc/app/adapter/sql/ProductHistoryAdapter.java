package mzc.app.adapter.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductHistoryAdapter extends ModelAdapter<ProductHistory> implements IProductHistoryAdapter {
    public ProductHistoryAdapter(@NotNull HikariDataSource ds) {
        super(ds);
    }

    @Override
    public @NotNull List<ProductHistory> getByBillId(long id) {
        var query = SqlFormatter.format("SELECT * FROM producthistory WHERE billId = ?",
                Collections.singletonList(id));
        try (var con = this.ds.getConnection(); var stmt = con.prepareStatement(query); var res = stmt.executeQuery()) {
            return deserializeResults(res);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    protected Class<ProductHistory> getType() {
        return ProductHistory.class;
    }
}
