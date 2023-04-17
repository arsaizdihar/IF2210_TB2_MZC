package mzc.app.adapter.xml;

import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;
import org.jetbrains.annotations.NotNull;

class CustomerAdapter extends FileCustomerAdapter<XMLLoader<Customer>> {
    private static final XMLLoader<Customer> loader = new XMLLoader<>();

    CustomerAdapter(@NotNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected @NotNull XMLLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
