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
    public HistoryTransactionEntryView(Set<ProductHistory> productHistories) {
        super();
        getViewModel().setProductHistories(productHistories);
    }
    @Override
    public @NotNull Node getView() {
        var root = getViewModel().getRoot();
        root.setMinWidth(0);
        root.setPrefWidth(1);

        var titleEntryBox = getViewModel().getTitleEntryBox();
        titleEntryBox.setMinWidth(0);
        titleEntryBox.setPrefWidth(1);
        titleEntryBox.setLeft(getViewModel().getInvoiceIdLabel());
        titleEntryBox.setRight(getViewModel().getDateLabel());
        root.getChildren().add(titleEntryBox);

        var itemsLabel = getViewModel().getItemsLabel();
        root.getChildren().add(itemsLabel);

        this.reloadSummary();

        var totalLabel = getViewModel().getTotalLabel();


        return root;
    }

    public void reloadSummary() {

        List<ItemPrice> itemPrices = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        for (ProductHistory productHistory : getViewModel().getProductHistories()) {
            BorderPane container = new BorderPane();

            ItemPrice itemPrice = new ItemPrice(productHistory.getAmount(), productHistory.getPriceView());
            Label nameLabel = new Label(productHistory.getName());
            Label price = new Label(itemPrice.toString());

            itemPrices.add(itemPrice);


            container.setLeft(nameLabel);
            container.setRight(price);
            getViewModel().getRoot().getChildren().add(container);
        }

        var subtotal = (new ItemListPrice(itemPrices).getValue());
        var subtotalView = PriceFactory.createPriceView(subtotal);
        getViewModel().getTotalLabel().setText(subtotalView.toString());
        getViewModel().getRoot().getChildren().add(getViewModel().getTotalLabel());

    }

}
