package mzc.app.adapter.json;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;

class CustomerAdapter extends FileCustomerAdapter<JSONLoader<Customer>> {
    private static final JSONLoader<Customer> loader = new JSONLoader<>();

    CustomerAdapter(@NonNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected JSONLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
