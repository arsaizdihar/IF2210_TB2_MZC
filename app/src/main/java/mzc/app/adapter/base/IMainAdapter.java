package mzc.app.adapter.base;

import org.jetbrains.annotations.NotNull;

public interface IMainAdapter {

    @NotNull ICustomerAdapter getCustomer();

    @NotNull IBillAdapter getBill();

    @NotNull IProductBillAdapter getProductBill();

    @NotNull IProductAdapter getProduct();

    @NotNull IProductHistoryAdapter getProductHistory();

    @NotNull IFixedBillAdapter getFixedBill();

    void close();

    void clearData();
}
