package mzc.app.view_model.components.report;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.modules.report.ProductHistoryTableUtil;
import mzc.app.view_model.base.BaseViewModel;

import java.util.Set;

public class ReportEntryViewModel extends BaseViewModel {
    @Getter
    VBox root = new VBox();
    @Getter @Setter
    private FixedBill fixedBill;
    @Getter @Setter
    private Set<ProductHistory> productHistories;

    @Getter
    private TableView<ProductHistory> table = new TableView<>();

    @Override
    public void init() {
        super.init();
        setProductHistories(getAdapter().getProductHistory().getByBillId(getFixedBill().getId()));
    }

    public void createTable() {
        table = new TableView<>(ProductHistoryTableUtil.createTable(getProductHistories()));
        table.getColumns().add(ProductHistoryTableUtil.getProductNameColumn());
        table.getColumns().add(ProductHistoryTableUtil.getImageColumn());
        table.getColumns().add(ProductHistoryTableUtil.getCategoryColumn());
        table.getColumns().add(ProductHistoryTableUtil.getBuyPriceColumn());
        table.getColumns().add(ProductHistoryTableUtil.getPriceColumn());
        table.getColumns().add(ProductHistoryTableUtil.getAmountColumn());

        table.setColumnResizePolicy(resizeFeatures -> true);
        table.setFixedCellSize(100);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));

        root.getChildren().add(table);
    }

    public void createPaymentInfo() {

        root.getChildren().add(new Label("No Resi : " + getFixedBill().getId()));

        var customer = getAdapter().getCustomer().getById(getFixedBill().getCustomerId());
        root.getChildren().add(new Label("Nama Pelanggan : " + customer.getName()));
        root.getChildren().add(new Label("Id Pelanggan : " + customer.getId()));

    }
}
