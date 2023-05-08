package mzc.app.view.components.member_list;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import lombok.NonNull;
import mzc.app.annotation.ModelInject;
import mzc.app.model.Customer;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.ItemListPrice;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.view.base.BaseView;
import mzc.app.view_model.base.BaseViewModel;
import mzc.app.view_model.components.member_list.HistoryTransactionEntryViewModel;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ModelInject(HistoryTransactionEntryViewModel.class)
public class HistoryTransactionEntryView extends BaseView<HistoryTransactionEntryViewModel> {
    public HistoryTransactionEntryView(Set<ProductHistory> productHistories, FixedBill fixedBill) {
        super();
        getViewModel().setProductHistories(productHistories);
        getViewModel().setFixedBill(fixedBill);
    }
    @Override
    public @NotNull Node getView() {
        var root = getViewModel().getRoot();
        root.setPadding(new javafx.geometry.Insets(5));

        root.getChildren().add(getViewModel().getPadder());
        var titleEntryBox = getViewModel().getTitleEntryBox();
        titleEntryBox.setMinWidth(0);
        titleEntryBox.setPrefWidth(1);
        root.getChildren().add(titleEntryBox);

        getViewModel().setEntryHeader();


        getViewModel().reloadSummary();

        var totalLabel = getViewModel().getTotalLabel();

        return root;
    }
}
