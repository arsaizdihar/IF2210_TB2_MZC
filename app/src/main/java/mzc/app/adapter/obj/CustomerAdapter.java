package mzc.app.adapter.obj;

import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;

class CustomerAdapter extends FileCustomerAdapter<OBJLoader<Customer>> {
    private static final OBJLoader<Customer> loader = new OBJLoader<>();

    CustomerAdapter(@NonNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected OBJLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
