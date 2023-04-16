package mzc.app.adapter.base;

import lombok.NonNull;

public interface IMainAdapter {

    @NonNull ICustomerAdapter getCustomer();

    @NonNull IBillAdapter getBill();

    void close();

    void clearData();
}
