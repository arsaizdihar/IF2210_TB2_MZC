package mzc.app.view_model.components.cashier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.model.Bill;
import mzc.app.model.ProductBill;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
import mzc.app.view.base.BaseView;
import mzc.app.view.components.cashier.CustomerSelectorView;
import mzc.app.view.components.cashier.ItemsView;
import mzc.app.view.components.cashier.SubtotalView;
import mzc.app.view_model.components.split_page.RightSideViewModel;
import mzc.app.view_model.page.CashierPageViewModel;

import java.util.HashMap;
import java.util.Map;

public class PaymentSummaryViewModel extends RightSideViewModel {
    public static class PaymentSummaryContext {
        @Getter
        private State<Map<ProductBill, ItemPrice>> productItems = new State<>(new HashMap<>());
    }

    @Getter
    protected CustomerSelectorView customerSelector = new CustomerSelectorView();

    @Getter
    protected BaseView<? extends SubtotalViewModel> totalView = new SubtotalView();

    @Getter
    protected ItemsView itemsView = new ItemsView();

    @Getter
    protected VBox container = new VBox();


    @Override
    public void init() {
        super.init();
        var paymentContext = new Context<>(new PaymentSummaryContext());
        addContext(paymentContext);

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();

        cashierContext.getBill().addListener((observableValue, prev, next) -> {
            reloadSummary(next);
        });

        createView(this.customerSelector);
        createView(this.itemsView);
        createView(this.totalView);
        reloadSummary(cashierContext.getBill().getValue());

        var title = new Label("Penjualan");
        title.getStyleClass().add("h3");
        title.setStyle("-fx-font-weight: bold;");

        var titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);

        container.setPadding(new Insets(10, 5, 10, 5));

        container.getChildren().add(titleBox);
        container.getChildren().add(getCustomerSelector().getView());
        container.getChildren().add(getItemsView().getView());
        var spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        container.getChildren().add(spacer);
        container.getChildren().add(getTotalView().getView());
    }

    public void reloadSummary(Bill bill) {
        var paymentContext = useContext(PaymentSummaryContext.class).getValue();
        var productBills = getAdapter().getBill().getProducts(bill);
        System.out.println("Reloading product summary (product bill) got size " + productBills.size());

        paymentContext.getProductItems().getValue().clear();

        productBills.forEach(productBill -> {
            if (productBill.getAmount() > 0) {
                ItemPrice itemPrice = new ItemPrice(productBill.getAmount(), PriceFactory.createPriceView(productBill.getProduct().getPrice()));
                paymentContext.getProductItems().getValue().put(productBill, itemPrice);
            }
        });

        paymentContext.getProductItems().forceUpdate();
    }
}
