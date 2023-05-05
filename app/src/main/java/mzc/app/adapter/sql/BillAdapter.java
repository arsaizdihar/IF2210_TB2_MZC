package mzc.app.adapter.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.ProductBill;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class BillAdapter extends ModelAdapter<Bill> implements IBillAdapter {
    @Setter
    private @NotNull ICustomerAdapter customerAdapter;

    @Getter
    private final @NotNull ProductBillAdapter productBillAdapter;

    public BillAdapter(@NotNull HikariDataSource ds, @NotNull ProductBillAdapter productBillAdapter) {
        super(ds);
        this.productBillAdapter = productBillAdapter;
    }

    @Override
    public @NotNull Set<Bill> getByCustomerId(long memberId) {
        var query = SqlFormatter.format("SELECT * FROM bill WHERE customerId = ?",
                Collections.singletonList(memberId));
        try (var con = ds.getConnection(); var stmt = con.prepareStatement(query); var rs = stmt.executeQuery()) {
            var models = deserializeResults(rs);
            return loadAllCustomers(models);
        } catch (SQLException e) {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public @NotNull Set<ProductBill> getProducts(Bill bill) {
        if (bill.isProductsLoaded()) {
            return bill.getProducts();
        }
        var products = productBillAdapter.getByBillId(bill.getId());
        bill.setProducts(products);
        bill.setProductsLoaded(true);
        return products;
    }

    public Set<Bill> loadAllCustomers(Set<Bill> bills) {
        var customers = customerAdapter.getInIds(bills.stream().map(Bill::getCustomerId).collect(Collectors.toSet()));
        var customersMap = customers.stream().collect(Collectors.toMap(Customer::getId, c -> c));
        bills.forEach(b -> b.setCustomer(customersMap.get(b.getCustomerId())));
        return bills;
    }

    @Override
    protected Class<Bill> getType() {
        return Bill.class;
    }
}
