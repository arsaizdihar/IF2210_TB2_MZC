package mzc.app.adapter.json;

import lombok.Getter;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.IMainAdapter;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class JSONAdapter implements IMainAdapter {
    @Getter
    private final @NotNull CustomerAdapter customer;
    @Getter
    private final @NotNull BillAdapter bill;
    @Getter
    private final @NotNull ProductAdapter product;
    @Getter
    private final @NotNull ProductHistoryAdapter productHistory;
    @Getter
    private final @NotNull FixedBillAdapter fixedBill;

    public JSONAdapter() {
        this.product = new ProductAdapter();
        this.bill = new BillAdapter(product);
        this.customer = new CustomerAdapter(bill);
        this.bill.setCustomerAdapter(this.customer);
        this.productHistory = new ProductHistoryAdapter();
        this.fixedBill = new FixedBillAdapter(productHistory);
        this.fixedBill.setCustomerAdapter(this.customer);
    }

    @Override
    public void close() {

    }

    @Override
    public void clearData() {
        try {
            FileUtils.deleteDirectory(new File(AdapterConfig.getBaseDataPath()));
        } catch (IOException ignored) {
        }
    }
}
