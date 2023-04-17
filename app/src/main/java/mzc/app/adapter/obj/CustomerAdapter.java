package mzc.app.adapter.obj;

import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;
import org.jetbrains.annotations.NotNull;

class CustomerAdapter extends FileCustomerAdapter<OBJLoader<Customer>> {
    private static final OBJLoader<Customer> loader = new OBJLoader<>();

    CustomerAdapter(@NotNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected @NotNull OBJLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
