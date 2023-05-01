package mzc.app.adapter.orm;

import lombok.NonNull;
import mzc.app.adapter.base.ICustomerAdapter;
import mzc.app.model.Bill;
import mzc.app.model.Customer;
import mzc.app.model.CustomerType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
    public List<Bill> getBills(@NotNull Customer customer) {
        return customer.getBills();
    }

    @Override
    public List<Customer> getRegisteredCustomer() {
        Query<Customer> query = session.createQuery("FROM customer C WHERE C.type <> :basic", Customer.class);
        query.setParameter("basic", CustomerType.BASIC);
        return query.list();
    }
}
