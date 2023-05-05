package mzc.app.adapter.orm;

import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import mzc.app.model.FixedBill;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

class CustomerAdapter extends ModelAdapter<Customer> implements ICustomerAdapter {
    public CustomerAdapter(Session session) {
        super(session);
    }

    @NotNull
    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }

    @Override
    public @NotNull Set<Bill> getBills(@NotNull Customer customer) {
        return customer.getBills();
    }

    @Override
    public @NotNull Set<FixedBill> getFixedBills(@NotNull Customer customer) {
        return customer.getFixedBills();
    }

    @Override
    public @NotNull Set<Customer> getRegisteredCustomer() {
        Query<Customer> query = session.createQuery("FROM customer C WHERE C.type <> :basic", Customer.class);
        query.setParameter("basic", CustomerType.BASIC);
        return query.stream().collect(Collectors.toSet());
    }
}
