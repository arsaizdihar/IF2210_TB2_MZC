package mzc.app.adapter.base;

import lombok.NonNull;

public interface IMainAdapter {

    @NonNull ICustomerAdapter getCustomer();

    @NonNull IBillAdapter getBill();

    @NonNull IProductAdapter getProduct();

    @NonNull IProductHistoryAdapter getProductHistory();

    @NonNull IFixedBillAdapter getFixedBill();

    void close();

    void clearData();
}
