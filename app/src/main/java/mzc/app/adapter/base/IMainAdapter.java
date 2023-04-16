package mzc.app.adapter.base;

import lombok.NonNull;

public interface IMainAdapter {

    @NonNull ICustomerAdapter getCustomer();

    @NonNull IBillAdapter getBill();

    @NonNull IProductAdapter getProduct();

    void close();

    void clearData();
}
