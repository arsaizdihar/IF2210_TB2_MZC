package mzc.app.adapter.json;

import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;
import org.jetbrains.annotations.NotNull;

class CustomerAdapter extends FileCustomerAdapter<JSONLoader<Customer>> {
    private static final @NotNull JSONLoader<Customer> loader = new JSONLoader<>();

    CustomerAdapter(@NotNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected @NotNull JSONLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
