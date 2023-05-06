package mzc.app.view_model.components.member_list;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.view_model.base.BaseViewModel;

import java.util.Set;

public class HistoryTransactionEntryViewModel extends BaseViewModel {

    @Getter @Setter
    private Set<ProductHistory> productHistories;
    @Getter
    VBox root = new VBox();
//    @Getter
//    HBox container = new HBox();
    @Getter
    BorderPane titleEntryBox = new BorderPane();
    @Getter
    Label invoiceIdLabel;
    @Getter
    Label dateLabel;
    @Getter
    Label itemsLabel = new Label("Items");
    @Getter
    Label totalLabel;
//    @Getter

    @Override
    public void init() {
        super.init();
    }

}
