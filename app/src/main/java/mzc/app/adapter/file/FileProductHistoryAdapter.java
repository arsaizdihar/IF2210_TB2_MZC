package mzc.app.adapter.file;

import lombok.AccessLevel;
import lombok.Setter;
import mzc.app.adapter.base.IFixedBillAdapter;
import mzc.app.adapter.base.IProductHistoryAdapter;
import mzc.app.model.ProductHistory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class FileProductHistoryAdapter extends FileModelAdapter<ProductHistory> implements IProductHistoryAdapter {
    @Setter(AccessLevel.PROTECTED)
    private IFixedBillAdapter billAdapter;

    protected FileProductHistoryAdapter(@NotNull IFileDataLoader loader) {
        super(loader);
    }

    @Override
    protected Class<ProductHistory> getType() {
        return ProductHistory.class;
    }

    @Override
    public @NotNull List<ProductHistory> getByBillId(long id) {
        var result = getClones(getData().values().stream().filter((v) -> v.getBillId() == id).collect(Collectors.toList()));
        result.forEach(p -> p.setBill(billAdapter.getById(p.getBillId())));
        return result;
    }
}
