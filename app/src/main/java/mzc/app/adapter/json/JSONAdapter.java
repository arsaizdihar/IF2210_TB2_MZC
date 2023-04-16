package mzc.app.adapter.json;

import lombok.Getter;
import lombok.NonNull;
import mzc.app.adapter.base.AdapterConfig;
import mzc.app.adapter.base.IMainAdapter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JSONAdapter implements IMainAdapter {
    @Getter private final @NonNull CustomerAdapter customer;
    @Getter private final @NonNull BillAdapter bill;

    public JSONAdapter() {
        this.bill = new BillAdapter();
        this.customer = new CustomerAdapter(bill);
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
