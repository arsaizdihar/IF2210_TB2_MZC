package mzc.app.adapter.file;

import lombok.Getter;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.IMainAdapter;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public abstract class FileAdapter implements IMainAdapter {
    @Getter
    private final @NotNull FileCustomerAdapter customer;

    @Getter
    private final @NotNull FileBillAdapter bill;
    @Getter
    private final @NotNull FileProductAdapter product;
    @Getter
    private final @NotNull FileProductHistoryAdapter productHistory;
    @Getter
    private final @NotNull FileFixedBillAdapter fixedBill;

    public FileAdapter(@NotNull IFileDataLoader loader) {
        this.product = new FileProductAdapter(loader);
        this.bill = new FileBillAdapter(loader, product);
        this.customer = new FileCustomerAdapter(loader, bill);
        this.bill.setCustomerAdapter(this.customer);
        this.productHistory = new FileProductHistoryAdapter(loader);
        this.fixedBill = new FileFixedBillAdapter(loader, productHistory);
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
