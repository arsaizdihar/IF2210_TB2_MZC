package mzc.app.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomerAdapter extends ModelAdapter<Customer> implements ICustomerAdapter {
    private final @NotNull IBillAdapter billAdapter;
    public CustomerAdapter(@NotNull HikariDataSource ds, @NotNull IBillAdapter billAdapter) {
        super(ds);
        this.billAdapter = billAdapter;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }

    @Override
    public @NotNull Set<Bill> getBills(@NotNull Customer customer) {
        if (customer.isBillsLoaded()) return customer.getBills();
        customer.setBillsLoaded(true);
        Set<Bill> result = billAdapter.getByCustomerId(customer.getId());
        customer.setBills(result);
        return result;
    }

    @Override
    public @NotNull Set<FixedBill> getFixedBills(@NotNull Customer customer) {
        return customer.getFixedBills();
    }

    @Override
    public @NotNull Set<Customer> getRegisteredCustomer() {
        try (var conn = ds.getConnection(); var stat = conn.prepareStatement("SELECT * FROM customer WHERE type <> 0"); var rs = stat.executeQuery()) {
            return deserializeResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedHashSet<>();
        }
    }
}
