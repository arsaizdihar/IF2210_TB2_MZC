package mzc.app.adapter.xml;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;

class CustomerAdapter extends FileCustomerAdapter<XMLLoader<Customer>> {
    private static final XMLLoader<Customer> loader = new XMLLoader<>();

    CustomerAdapter(@NonNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected XMLLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
