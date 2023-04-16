package mzc.app.adapter.obj;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.IBillAdapter;
import mzc.app.adapter.file.FileCustomerAdapter;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;

class CustomerAdapter extends FileCustomerAdapter<OBJLoader<Customer>> {
    private static final OBJLoader<Customer> loader = new OBJLoader<>();

    CustomerAdapter(@NonNull IBillAdapter billAdapter) {
        super(billAdapter);
    }

    @Override
    protected @NonNull OBJLoader<Customer> getLoader() {
        return loader;
    }

    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }
}
