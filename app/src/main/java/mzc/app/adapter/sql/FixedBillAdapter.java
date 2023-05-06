package mzc.app.adapter.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FixedBillAdapter extends ModelAdapter<FixedBill> implements IFixedBillAdapter {
    private final @NotNull ProductHistoryAdapter productHistoryAdapter;
    @Setter
    private @NotNull ICustomerAdapter customerAdapter;

    public FixedBillAdapter(@NotNull HikariDataSource ds, @NotNull ProductHistoryAdapter productHistoryAdapter) {
        super(ds);
        this.productHistoryAdapter = productHistoryAdapter;
    }

    @Override
    public @NotNull Set<FixedBill> getByCustomerId(long customerId) {
        var query = SqlFormatter.format("SELECT * FROM fixedbill WHERE customerId = ?",
                Collections.singletonList(customerId));
        try (var con = this.ds.getConnection(); var stmt = con.prepareStatement(query); var rs = stmt.executeQuery()) {
            return loadAllCustomers(deserializeResults(rs));
        } catch (SQLException e) {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public FixedBill getById(long id) {
        var model = super.getById(id);
        model.setCustomer(customerAdapter.getById(model.getCustomerId()));
        return model;
    }

    @Override
    public @NotNull Set<ProductHistory> getProducts(FixedBill bill) {
        if (bill.isProductsLoaded()) {
            return bill.getProducts();
        }
        var products = productHistoryAdapter.getByBillId(bill.getId());
        bill.setProducts(products);
        bill.setProductsLoaded(true);
        return products;
    }

    public Set<FixedBill> loadAllCustomers(Set<FixedBill> bills) {
        var customers = customerAdapter.getInIds(bills.stream().map(FixedBill::getCustomerId).collect(Collectors.toSet()));
        var customersMap = customers.stream().collect(Collectors.toMap(Customer::getId, c -> c));
        bills.forEach(b -> b.setCustomer(customersMap.get(b.getCustomerId())));
        return bills;
    }

    @Override
    protected Class<FixedBill> getType() {
        return FixedBill.class;
    }
}
