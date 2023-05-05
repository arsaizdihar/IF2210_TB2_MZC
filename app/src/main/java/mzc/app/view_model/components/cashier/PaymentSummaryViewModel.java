package mzc.app.view_model.components.cashier;

import javafx.scene.layout.VBox;
import lombok.Getter;
import mzc.app.model.ProductBill;
import mzc.app.modules.pricing.PriceFactory;
import mzc.app.modules.pricing.price.ItemPrice;
import mzc.app.utils.reactive.Context;
import mzc.app.utils.reactive.State;
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
    private CustomerSelectorView customerSelector = new CustomerSelectorView();

    @Getter
    private SubtotalView totalView = new SubtotalView();

    @Getter
    private ItemsView itemsView = new ItemsView();

    @Getter
    private VBox container = new VBox();


    @Override
    public void init() {
        super.init();

        var paymentContext = new Context<>(new PaymentSummaryContext());
        addContext(paymentContext);

        var cashierContext = useContext(CashierPageViewModel.CashierContext.class).getValue();

        cashierContext.getBill().addListener((observableValue, bill, t1) -> {
            reloadSummary();
        });

        createView(this.customerSelector);
        createView(this.itemsView);
        createView(this.totalView);
        reloadSummary();
    }

    public void reloadSummary() {
        var paymentContext = useContext(PaymentSummaryContext.class).getValue();
        var context = useContext(CashierPageViewModel.CashierContext.class).getValue();
        var productBills = getAdapter().getBill().getProducts(context.getBill().getValue());
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
