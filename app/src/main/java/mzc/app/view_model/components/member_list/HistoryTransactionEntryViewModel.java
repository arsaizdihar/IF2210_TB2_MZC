package mzc.app.view_model.components.member_list;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import mzc.app.model.FixedBill;
import mzc.app.model.ProductHistory;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.ItemListPrice;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.view_model.base.BaseViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HistoryTransactionEntryViewModel extends BaseViewModel {

    @Getter @Setter
    private FixedBill fixedBill;
    @Getter @Setter
    private Set<ProductHistory> productHistories;
    @Getter
    VBox root = new VBox();
//    @Getter
//    HBox container = new HBox();
    @Getter
    BorderPane titleEntryBox = new BorderPane();
    @Getter
    Label invoiceIdLabel = new Label("Invoice ID: ");
    @Getter
    Label dateLabel = new Label("Date: ");
    @Getter
    Label itemsLabel = new Label("Items");
    @Getter
    Label totalLabel = new Label("Total");
    @Getter
    HBox padder = new HBox();
//    @Getter

    @Override
    public void init() {
        super.init();

    }
    public void reloadSummary() {

        List<ItemPrice> itemPrices = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        for (ProductHistory productHistory : getProductHistories()) {
            BorderPane container = new BorderPane();

            ItemPrice itemPrice = new ItemPrice(productHistory.getAmount(), productHistory.getPriceView());
            Label nameLabel = new Label(productHistory.getName());
            Label price = new Label(itemPrice.toString());

            nameLabel.getStyleClass().add("h5");

            price.getStyleClass().add("h5");

            itemPrices.add(itemPrice);

            container.setLeft(nameLabel);
            container.setRight(price);
            container.setPadding(new Insets(0, 35, 0, 20));
            getRoot().getChildren().add(container);
        }

        var subtotal = (new ItemListPrice(itemPrices).getValue());
        var subtotalView = PriceFactory.createPriceView(subtotal);

        var textTotal = new Label("Total");
        textTotal.getStyleClass().add("h5");

        getTotalLabel().setText(subtotalView.toString());
        getTotalLabel().getStyleClass().add("h5");


        VBox totalBox = new VBox();
        totalBox.getChildren().add(textTotal);
        totalBox.getChildren().add(getTotalLabel());
        totalBox.setPadding(new Insets(0, 35, 0, 20));
        totalBox.setAlignment(Pos.CENTER_RIGHT);


        getRoot().getChildren().add(totalBox);

    }
    public void setEntryHeader() {
        itemsLabel.getStyleClass().add("h5");
        invoiceIdLabel.getStyleClass().add("h4");
        dateLabel.getStyleClass().add("h5");

        getDateLabel().setText("Tanggal: " + fixedBill.getCreatedAt().toString());
        getInvoiceIdLabel().setText("No Faktur: " + getFixedBill().getId());

        getTitleEntryBox().setLeft(itemsLabel);
        getTitleEntryBox().setRight(dateLabel);
        getTitleEntryBox().setPadding(new Insets(0, 35, 0, 20));

        padder.getChildren().add(invoiceIdLabel);
        padder.setPadding(new Insets(0, 35, 0, 10));
    }
}

